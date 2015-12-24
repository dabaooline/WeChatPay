package net.sourceforge.simcpux.util;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import net.sourceforge.simcpux.bean.PrePayListInfo;
import net.sourceforge.simcpux.bean.ProductList;

import java.io.IOException;

/**
 * Created by admin on 2015/12/17.
 */
public class WXCreatePayListUtils {

    private String mAppId=null;
    private Context mContext=null;
    private CreatePrepayOrderListListener createPrepayOrderListListener = null;


    /***
     *
     * @param context  上下文环境
     * @param appid    由微信支付官方提供
     */
    public WXCreatePayListUtils(Context context, String appid) {
        this.mContext = context;
        this.mAppId = appid;
    }


    /***
     *
     * @param productList  订单信息的bean类,可自行实现
     * @param requestUrl  请求生成预支付订单的地址(由后台提供)
     * @param oneKeyPay  是否支持一键支付(所谓一键支付,就是直接跳转到支付界面)
     */
    public void createPrepayOrderList(final ProductList productList, final String requestUrl, final boolean oneKeyPay) {

        new AsyncTask<Void, Void, PrePayListInfo>() {

            @Override
            protected void onPreExecute() {
                //在这里执行回调方法
                if (createPrepayOrderListListener != null) {
                    createPrepayOrderListListener.onPreCreate();
                }
                super.onPreExecute();
            }

            @Override
            protected PrePayListInfo doInBackground(Void... params) {
                //在这里执行回调方法
                if (createPrepayOrderListListener != null) {
                    createPrepayOrderListListener.doInCreate();
                }

                //将订单数据转化成Json,通过post请求进行传输
                Gson gson = new Gson();
                String s = gson.toJson(productList);
                try {
                   byte[] result = HttpUtils.httpPost(requestUrl, s.getBytes("UTF-8"));
//                    byte[] result=HttpUtils.httpGet(requestUrl);
                    //由于传来的参数有一个key是package,所以需要将该key转化一下,不然不能用
                    System.out.println("test++"+new String(result));
                    return gson.fromJson(new String(result).replace("package", "packageName"), PrePayListInfo.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(PrePayListInfo paramsBean) {
                if (createPrepayOrderListListener != null) {
                    createPrepayOrderListListener.onPostCreate(paramsBean);
                }

                if (oneKeyPay) {
                    PayUtils.onKeyPay(mContext,mAppId,paramsBean);
                }
                super.onPostExecute(paramsBean);
            }
        }.execute();
    }


    /***
     * 设置创建订单的监听
     *
     * @param listListener
     */
    public void setCreatePrepayOrderListListener(CreatePrepayOrderListListener listListener) {
        this.createPrepayOrderListListener = listListener;
    }

    /***
     * 由于预支付订单的产生过程是一个异步过程,所以提供一个接口
     * 创建订单过程监听
     */
    public interface CreatePrepayOrderListListener {
        /***
         * 请求创建前,在UI线程中,由于创建订单较为耗时,
         * 可以在这个方法里面执行一些提高用户体验的显示操作:例如进度条
         */
        public void onPreCreate();

        /***
         * 请求创建中,在子线程中,注意不要有一些UI更新
         */
        public void doInCreate();

        /***
         * 请求完毕后执行
         */
        public void onPostCreate(PrePayListInfo prePayListInfo);
    }
}
