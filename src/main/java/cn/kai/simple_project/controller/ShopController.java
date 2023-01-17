package cn.kai.simple_project.controller;

import cn.kai.simple_project.common.domain.JsonData;
import cn.kai.simple_project.dto.Shop;
import cn.kai.simple_project.service.ShopService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 附近商铺简单实现
 * Author: chenKai
 * Date: 2023/1/16
 */
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    /**
     * 附近商铺简单实现
     * @return
     */
    @PostMapping(value = "/getNearbyShopList")
    public JsonData getNearbyShopList(@RequestBody Shop shop){
        return JsonData.buildSuccess(shopService.getNearbyShopList(shop));
    }

}
