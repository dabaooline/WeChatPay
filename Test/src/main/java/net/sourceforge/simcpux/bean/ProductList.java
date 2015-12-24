package net.sourceforge.simcpux.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户选购商品的信息,封装成一个bean类,最终以json的形式提交至后台,
 * 后台返回预支付订单信息
 *
 * 以下写法只是个设想,具体再商议
 * Created by admin on 2015/12/17.
 */
public class ProductList {

    private static Map<String, Integer> map = new HashMap<String, Integer>();

    public static Map<String,Integer> addProduct(String product) {
        Integer integer = map.get(product);
        map.put(product, integer + 1);
        return map;
    }

    public static Map<String, Integer> addProduct(String product, Integer count) {
        map.put(product, count);
        return map;
    }


}
