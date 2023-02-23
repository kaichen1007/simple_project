package cn.kai.simple_project.config.aop;

import cn.kai.simple_project.common.utils.SystemUtil;
import cn.kai.simple_project.config.annot.BankApi;
import cn.kai.simple_project.config.annot.LogApi;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.SimpleFormatter;
import java.util.stream.Stream;

/**
 * Author: chenKai
 * Date: 2023/2/21
 */
@Aspect
@Component
@Slf4j
public class AopByAnno {



    private final ObjectMapper inoutJson = initInputJackson();


    /**
     * 获取切入点
     */
    @Pointcut("@annotation(cn.kai.simple_project.config.annot.LogApi)")
    public void point(){}

    @Pointcut("within(cn.kai..controller..*)")
    public void point2(){}

    @Pointcut("within(cn.kai..service..*)")
    public void point3(){}

    /**
     * 切面
     *
     * ip
     * 请求地址
     * 用户信息
     * 类名
     * 方法名
     * 浏览器的信息
     * 客户端类型
     * 系统
     * 入参
     * 出参
     * 时间
     * 方法的解释
     * 操作的类型
     * 耗时
     * @param joinPoint
     * @return
     */
    @Around("point2()")
    public Object logAop(ProceedingJoinPoint joinPoint) throws Throwable {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sf.format(new Date());
        log.info("开始时间:{}",format);
        long begin = System.currentTimeMillis();


        Class<?> objectClass = joinPoint.getTarget().getClass();
        log.info("类名:{}",objectClass.getName());
        MethodSignature  methodSignature =  (MethodSignature)joinPoint.getSignature();
        log.info("方法名:{}",methodSignature.getName());
        LogApi annotation = methodSignature.getMethod().getAnnotation(LogApi.class);
        log.info("方法:{},操作类型:{}",annotation.methodDesc(),annotation.type());
        HttpServletRequest request = SystemUtil.getRequest();
        if (Objects.nonNull(request)){
            String realIp = SystemUtil.getRealIp(request);
            log.info("ip:{}",realIp);
            String requestURI = request.getRequestURI();
            log.info("请求地址:{}",requestURI);
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
            String browerName = userAgent.getBrowser().name();
            log.info("browerName:{}",browerName);
            String os = userAgent.getOperatingSystem().getName();
            log.info("os:{}",os);
            String client = userAgent.getOperatingSystem().getDeviceType().name();
            log.info("客户端:{}",client);
        }

        //开始获取入参
        String[] parameterNames = methodSignature.getParameterNames();
        Class[] parameterTypes = methodSignature.getParameterTypes();
        Class returnType = methodSignature.getReturnType();
        Object[] args = joinPoint.getArgs();
        List<String> argsList = new ArrayList<>();

        for (int i = 0; i < parameterNames.length; i++) {
            String argValue = inoutJson.writeValueAsString(args[i]);
            if (argValue != null) {
                int argValueLength = argValue.length();
                if (argValueLength > 1024) {
                    argValue = argValue.substring(0, 200) + "...(length: " + argValueLength + ")";
                }
            }
            argsList.add("[" + parameterTypes[i].getSimpleName() + "]" + parameterNames[i] + "=" + argValue);
        }

        String requestParams = String.join(",",argsList);
        log.info("入参:{}",requestParams);

        try {
            Object proceed = joinPoint.proceed();
            String format1 = sf.format(new Date());
            long end = System.currentTimeMillis();
            log.info("结束时间:{}",format1);
            log.info("用时:{}",(end - begin) /1000);
            log.info("出参[:{}:{}]",returnType,inoutJson.writeValueAsString(proceed));
            return proceed;
        }catch (Throwable e){
            log.error("异常:{}",e.toString());
            throw e;
        }
    }

    private ObjectMapper initInputJackson() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //custom serialize
        builder.serializerByType(MultipartFile.class, new JsonSerializer<MultipartFile>() {
            @Override
            public void serialize(MultipartFile value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeStringField("originalFilename", value.getOriginalFilename());
                gen.writeNumberField("size", value.getSize());
                gen.writeStringField("contentType", value.getContentType());
                gen.writeEndObject();
            }
        });
        Stream.of(
                ServletRequest.class,
                ServletResponse.class,
                byte[].class,
                Workbook.class,
                HSSFWorkbook.class,
                XSSFWorkbook.class,
                Throwable.class
        ).forEach(clazz -> builder.serializerByType(clazz, new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString("[...]");
            }
        }));

        ObjectMapper result = builder.build();
        result.disable(MapperFeature.USE_ANNOTATIONS);
        return result;
    }

}
