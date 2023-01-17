package cn.kai.simple_project.service.impl;

import cn.kai.simple_project.dto.Shop;
import cn.kai.simple_project.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author: chenKai
 * Date: 2023/1/16
 */
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {


    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 附近商铺简单实现
     * @param shop
     * @return
     */
    @Override
    public List<Shop> getNearbyShopList(Shop shop) {
        //判断此区域是否存在redis
        String key  = "bj:1";
        if (!redisTemplate.hasKey(key)){
            //如果不存在则插入 此操作可从库中查数据
            List<Shop> shopList = new ArrayList<>();
            Shop tam = new Shop(1, "tam", null, "116.397469", "39.908821", null);
            Shop gug = new Shop(1, "gug", null, "116.397027", "39.918056", null);
            Shop bhgy = new Shop(1, "bhgy", null, "116.389977", "39.933144", null);
            shopList.add(tam);
            shopList.add(gug);
            shopList.add(bhgy);
            //根据指定字段进行分组 指定字段相同则分到同组
            Map<Integer, List<Shop>> shopMap = shopList.stream().collect(Collectors.groupingBy(Shop::getShopId));
            for (Map.Entry<Integer, List<Shop>> entry : shopMap.entrySet()) {

                //同组为同一个地理区域
                String addKey = "bj:".concat(entry.getKey().toString());

                //获取同类型的店铺集合
                List<Shop> entryValue = entry.getValue();
                List<RedisGeoCommands.GeoLocation<String>> locations = new ArrayList<>(entryValue.size());
                //写入redis
                entryValue.forEach(obj->{
                    locations.add(
                            new RedisGeoCommands.GeoLocation<>(
                                    //测试用店铺姓名  实际用店铺id后续从数据库中查出相关数据
                                    obj.getShopName(),new Point(Double.parseDouble(obj.getX()),Double.parseDouble(obj.getY()))
                            )
                    );
                });
                redisTemplate.opsForGeo().add(addKey,locations);
            }
        }

        //根据坐标 取出附近五公里商家
        //查询redis，按照距离排序、分页  结果: shopName(店铺名称) distance(距离)
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().search(
                key,
                GeoReference.fromCoordinate(Double.parseDouble(shop.getX()), Double.parseDouble(shop.getY())),
                new Distance(5000),
                RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().includeDistance().limit(10)
        );

        if (results == null){
            return null;
        }
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = results.getContent();

        //根据redis 组装返回对象
        //实际可以根据id和距离进行组装map   根据到数据库中in出来店铺信息查出距离  减少循环查询增加性能\
        Map<String,String> map = new HashMap<>();
        content.forEach(obj->{
            map.put(obj.getContent().getName(), String.valueOf(obj.getDistance().getValue()));
        });

        List<Shop> resultList = new ArrayList<>();
        content.forEach(obj->{
            Shop item = new Shop();
            item.setShopName(obj.getContent().getName());
            item.setDistance(String.valueOf(obj.getDistance().getValue()));
            resultList.add(item);
        });
        return resultList;
    }
}
