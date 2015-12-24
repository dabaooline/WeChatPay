package net.sourceforge.simcpux.util;

import android.content.Context;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import net.sourceforge.simcpux.bean.PrePayListInfo;

/**
 * Created by admin on 2015/12/18.
 */
public class PayUtils {


    private PayUtils(){}

    /***
     * 此处注册微信支付,如已注册此处可忽略
     * @param context    应用的上下文环境
     * @param appId     微信支付提供的appId
     */
    public static IWXAPI registerAPP(Context context, String appId) {
        return WXAPIFactory.createWXAPI(context, appId);
    }

    public static void pay(PrePayListInfo payParamsBean,IWXAPI wxappi){
        if (payParamsBean.hasNoNullField()) {
            PayReq payReq = new PayReq();
            payReq.appId = payParamsBean.getAppid();
            payReq.partnerId = payParamsBean.getPartnerid();
            payReq.prepayId = payParamsBean.getPrepayid();
            payReq.nonceStr = payParamsBean.getNoncestr();
            payReq.timeStamp = payParamsBean.getTimestamp();
            payReq.packageValue = payParamsBean.getPackageName();
            payReq.sign = payParamsBean.getSign();
            payReq.extData = "app data"; // optional
            System.out.println("PayReq:" + payParamsBean.toString());
            wxappi.sendReq(payReq);
        } else {

        }
    }


    /***
     * 用于一键支付
     * @param context    应用的上下文环境
     * @param appId     微信支付提供的appId
     * @param payParamsBean  预支付订单bean类
     */
    public static void onKeyPay(Context context,String appId,PrePayListInfo payParamsBean) {
        pay(payParamsBean,registerAPP(context,appId));
    }
}
