package com.example.handsomefu.passwordbook.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.handsomefu.passwordbook.MyApplication;
import com.example.handsomefu.passwordbook.utils.ScreenUtils;

import java.util.List;


/**
 * Created by HandsomeFu on 2016/10/25.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        ScreenUtils l = new ScreenUtils(this);
        l.begin(new ScreenUtils.ScreenStateListener() {

            @Override
            public void onUserPresent() {
            }

            @Override
            public void onScreenOn() {
            }

            @Override
            public void onScreenOff() {
                MyApplication.setPasswordFlag(false);
            }
        });
    }

    public abstract void initView();

    public abstract int getLayout();

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
            //全局变量isActive = false 记录当前已经进入后台
            MyApplication.setPasswordFlag(false);
        }
        System.out.println("onStop isActive: " + MyApplication.getPasswordFlag());
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (!MyApplication.getPasswordFlag()) {
            //   app 从后台唤醒，进入前台
            startActivity(new Intent(this, AwakePasswordActivity.class));
            MyApplication.setPasswordFlag(true);
        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
