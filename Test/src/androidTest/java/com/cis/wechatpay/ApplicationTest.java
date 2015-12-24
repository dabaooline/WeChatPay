package com.cis.wechatpay;

import android.app.Application;
import android.test.ApplicationTestCase;

import net.sourceforge.simcpux.util.HttpUtils;

import org.json.JSONException;

import java.io.IOException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private String s;

    public ApplicationTest() {
        super(Application.class);
    }

    public void test(){
        try {
            byte[] bytes = HttpUtils.httpGet("http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android");
            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * httpPost测试
     */

    public void test2(){
        String s= "<xml><appid>wx2421b1c4370ec43b</appid><attach>支付测试</attach><body>JSAPI支付测试</body><mch_id>10000100</mch_id><nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str><notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url><openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid><out_trade_no>1415659990</out_trade_no><spbill_create_ip>14.23.150.211</spbill_create_ip><total_fee>1</total_fee><trade_type>JSAPI</trade_type><sign>0CB01533B8C1EF103065174F50BCA001</sign></xml>";
        try {
            byte[] bytes = HttpUtils.httpPost("https://api.mch.weixin.qq.com/pay/unifiedorder", s.getBytes());
            System.out.println("test2"+(new String(bytes)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test3() throws JSONException {
    }
}