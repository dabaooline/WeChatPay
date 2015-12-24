package net.sourceforge.simcpux.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 这是一个网络请求的工具类,基于HttpUrlConnection,可实现get和post请求
 * Created by admin on 2015/12/15.
 */
public class HttpUtils {

    /***
     *
     * @param path 完整的get请求路径
     * @return  以字节数组的形式返回响应数据
     * @throws IOException
     */
    public static byte[] httpGet(String path) throws IOException {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream inStream = null;
        try {
            url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode=connection.getResponseCode();
            if (statusCode == 200) {
                inStream = connection.getInputStream();
                System.out.println("test:test"+inStream.available());
                return StreamUtils.inputStreamToByte(inStream);
            } else {
                System.out.println("错误码为:"+statusCode);
            }
        } finally {
            if (connection != null) {
               connection.disconnect();
            }
        }
        return null;
    }

    /***
     * 通过HttpUrlConnection建立post连接
     * @param path  post请求的地址
     * @param bytes  请将post请求内容转化成字节数组
     * @return  以字节数组的形式返回响应数据
     * @throws IOException
     */
    public static byte[] httpPost(String path, byte[] bytes) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = null;
        InputStream resultStream=null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.getOutputStream().write(bytes);
            resultStream=connection.getInputStream();
            return StreamUtils.inputStreamToByte(resultStream);

        } finally {
            //关闭连接,关闭流
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

}
