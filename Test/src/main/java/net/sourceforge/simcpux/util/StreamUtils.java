package net.sourceforge.simcpux.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by admin on 2015/12/16.
 */
public class StreamUtils {
    /***
     * 将输入流转换成字节数组
     *
     * @param inputStream
     * @return
     */
    public static byte[] inputStreamToByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = null;

            byteArrayOutputStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = inputStream.read()) != -1) {
                byteArrayOutputStream.write(ch);
            }
            return byteArrayOutputStream.toByteArray();


        /*
        //此处不建议关闭流,因为关闭byteArrayOutputStream间接就关闭了inputStream,不关闭inputStream的原因
        //是为inputStream在其他地方的使用提供便利
        finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }*/
    }

    /***
     * 将字节数组转化成输出流
     * @param bytes
     * @return
     * @throws IOException
     */
    public static OutputStream bytesToOutputStream(byte[] bytes) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(bytes);
        return byteArrayOutputStream;
    }
}

