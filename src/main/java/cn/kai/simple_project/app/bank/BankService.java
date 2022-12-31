package cn.kai.simple_project.app.bank;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.common.utils.HttpsUtils;
import cn.kai.simple_project.config.annot.BankApi;
import cn.kai.simple_project.config.annot.BankApiField;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.message.callback.SecretKeyCallback;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Author: chenKai
 * Date: 2022/12/31
 */
@Service
@Slf4j
public class BankService {

    public String remoteCall(AbstractApi api){

        //获取注解值
        BankApi bankApi = api.getClass().getAnnotation(BankApi.class);
        String url = bankApi.url();

        StringBuilder sb = new StringBuilder();
        JSONObject data = new JSONObject();

        Arrays.stream(
                api.getClass().getDeclaredFields()//获取所有字段
        ).filter(field -> field.isAnnotationPresent(BankApiField.class))//查看是否标记了注解 对没有标记注解对进行过滤
                .sorted(Comparator.comparing(order->order.getAnnotation(BankApiField.class).order()))//根据注解对字段进行排序
        .peek(field -> field.setAccessible(true))//对私有字段进行暴力访问
        .forEach(field -> {
            //开始进行处理字段

            //获取注解
            BankApiField bankApiField = field.getAnnotation(BankApiField.class);
            Object value = "";

            //反射获取字段值
            try {
                value = field.get(api);
            } catch (IllegalAccessException e) {
                log.error("反射获取字段值失败");
                e.printStackTrace();
            }
            //根据字段类型正确填充字段
            switch (bankApiField.type()){
                case "S":{
                    //字符串处理
                    String result = String.format("%-" + bankApiField.length() + "s", value.toString()).replace(' ', '_');
                    sb.append(result);
                    data.put(field.getName(),result);
                    break;
                }
                case "N":{
                    //数字处理
                    String result = String.format("%" + bankApiField.length() + "s", value.toString()).replace(' ', '0');
                    sb.append(result);
                    data.put(field.getName(),result);
                    break;
                }
                case "M":{
                    //小数处理
                    if (!(value instanceof BigDecimal)){
                        throw new RuntimeException(String.format("{} 的 {} 必须是BigDecimal", api, field));
                    }
                    String format = String.format("%0" + bankApiField.length() + "d"
                            , ((BigDecimal) value).setScale(2, RoundingMode.DOWN)
                                    .multiply(new BigDecimal("100")).longValue());//金额向下舍入 2 位到分，以分为单位，作为数字类型同样进行左填充
                    sb.append(format);
                    data.put(field.getName(),format);
                    break;
                }
                default:
                    break;
            }

        });
        //发送参数
        String param = data.toJSONString();
        long begin = System.currentTimeMillis();
        Map<String,String> map = new HashMap<>();
        map.put("param",param);
        String s = HttpsUtils.doGetCallString("http://localhost:8081" + url, map);
        log.info("调用银行API{} 的 url:{}, 参数:{}, 耗时:{}",bankApi.desc(), bankApi.url(),param,System.currentTimeMillis() - begin);
        log.info("json数据:{}",data.toJSONString());
        return s;
    }

}
