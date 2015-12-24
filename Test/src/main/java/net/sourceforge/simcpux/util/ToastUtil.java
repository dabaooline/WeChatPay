package net.sourceforge.simcpux.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2015/12/18.
 */
public class ToastUtil {

    private static Toast toast;
    public static void showToast(Context context,String text,int duration){
        if(toast==null){
            toast = Toast.makeText(context, text,duration);
        }
        toast.setText(text);
        toast.show();
    }
}
