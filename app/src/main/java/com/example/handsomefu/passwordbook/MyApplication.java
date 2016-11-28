package com.example.handsomefu.passwordbook;

import android.app.Application;
import android.content.Context;

/**
 * Created by HandsomeFu on 2016/10/26.
 *
 * Application对象的生命周期是整个程序中最长的，
 * 它的生命周期就等于这个程序的生命周期。
 * 因为它是全局的单例的，
 * 所以在不同的Activity,Service中获得的对象都是同一个对象。
 * 所以可以通过Application来进行一些，如：数据传递、数据共享和数据缓存等操作.*/
public class MyApplication extends Application {
    private static Context mContext;
    private static boolean passwordFlag = true;
    public static boolean getPasswordFlag() {
        return passwordFlag;
    }

    public static void setPasswordFlag(boolean passwordFlag) {
        MyApplication.passwordFlag = passwordFlag;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Application onCreate");
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
