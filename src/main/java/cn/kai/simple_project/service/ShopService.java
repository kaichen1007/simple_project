package cn.kai.simple_project.service;

import cn.kai.simple_project.dto.Shop;

import java.util.List;

/**
 * Author: chenKai
 * Date: 2023/1/16
 */
public interface ShopService {
    /**
     * 附近商铺简单实现
     * @param shop
     * @return
     */
    List<Shop> getNearbyShopList(Shop shop);
}
