package com.example.handsomefu.passwordbook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.handsomefu.passwordbook.MyApplication;
import com.example.handsomefu.passwordbook.R;
import com.example.handsomefu.passwordbook.ToastUtils;
import com.example.handsomefu.passwordbook.utils.CommonUtils;
import com.example.handsomefu.passwordbook.utils.HexUtil;
import com.example.handsomefu.passwordbook.utils.SPUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HandsomeFu on 2016/10/28.
 */
public class AwakePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etPw;
    private Button btIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_awake_password);
        etPw = (EditText) findViewById(R.id.et_pw);
        CommonUtils.autoSoftKeyboard(etPw);
        btIn = (Button) findViewById(R.id.bt_in);
        btIn.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ToastUtils.toastShort("返回也没有用");
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_in:
                String inputPw = etPw.getText().toString();
                String rightPw = SPUtils.getPW();
                boolean ifRight;
                try {
                    ifRight = HexUtil.validPasswd(inputPw, rightPw);
                } catch (NoSuchAlgorithmException e) {
                    ifRight = false;
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    ifRight = false;
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(inputPw) && ifRight) {
                    MyApplication.setPasswordFlag(true);
                    finish();
                } else {
                    ToastUtils.toastShort("密码输入错误");
                    etPw.setText("");
                }
                break;
        }
    }
}
