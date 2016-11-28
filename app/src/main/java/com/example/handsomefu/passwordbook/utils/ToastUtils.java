package com.example.handsomefu.passwordbook;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HandsomeFu on 2016/10/26.
 */
public class ToastUtils {

    public static void toastLong(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
    public static void toastShort(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
