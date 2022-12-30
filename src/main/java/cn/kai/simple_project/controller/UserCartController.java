package cn.kai.simple_project.controller;

import cn.kai.simple_project.common.comenum.BizCodeEnum;
import cn.kai.simple_project.common.domain.CommonRequestDomain;
import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.dto.Cart;
import cn.kai.simple_project.model.AbstractCartModel;
import org.apache.catalina.core.StandardContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static cn.kai.simple_project.common.utils.StringUtils.*;

/**
 * 用户购物车控制层
 * Author: chenKai
 * Date: 2022/12/30
 */
@RestController
@RequestMapping(value = "/userCart")
public class UserCartController {

    @Resource
    private ApplicationContext applicationContext;

    @PostMapping(value = "/getUserCar")
    public JsonData getUserCar(@RequestBody CommonRequestDomain commonRequestDomain){
        //根据用户id获取用户信息
        String userType = firstCharUpperCase(commonRequestDomain.getType().toLowerCase());
        AbstractCartModel cart = (AbstractCartModel) applicationContext.getBean( userType+"UserCart");
        Map<Long,Integer> count1 = new HashMap<>();
        count1.put(2L,1);
        count1.put(3L,3);
        count1.put(4L,3);
        return JsonData.buildSuccess(cart.process(1L,count1));
    }

}
