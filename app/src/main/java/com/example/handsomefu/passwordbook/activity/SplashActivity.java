package com.example.handsomefu.passwordbook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.handsomefu.passwordbook.Constant;
import com.example.handsomefu.passwordbook.MyApplication;
import com.example.handsomefu.passwordbook.R;

public class SplashActivity extends Activity {
    private static final int SPLASH = 4;
    private ImageView ivSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        MyApplication.setPasswordFlag(true);
        initView();
        startAnimation();
    }

    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, PasswordActivity.class);
                intent.putExtra(Constant.FROM, SPLASH);
                startActivity(intent);
                //startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setFillAfter(true);
        ivSplash.startAnimation(animation);
    }

    protected void initView() {
        ivSplash = (ImageView) findViewById(R.id.iv_splash);
    }

    public int getLayout() {
        return R.layout.ac_splash;
    }
}
