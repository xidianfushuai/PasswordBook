package com.example.handsomefu.passwordbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.handsomefu.passwordbook.Constant;
import com.example.handsomefu.passwordbook.R;
import com.example.handsomefu.passwordbook.ToastUtils;
import com.example.handsomefu.passwordbook.utils.CommonUtils;
import com.example.handsomefu.passwordbook.utils.HexUtil;
import com.example.handsomefu.passwordbook.utils.SPUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HandsomeFu on 2016/10/26.
 */
public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llSetPw;
    private EditText etPw1;
    private EditText etPw2;
    private LinearLayout llInputPw;
    private EditText etPw;
    private Button btIn;
    private Button btFinish;
    Intent intent;
    int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        boolean seted = SPUtils.getPWStatus();
        if (seted) {
            llInputPw.setVisibility(View.VISIBLE);
            CommonUtils.autoSoftKeyboard(etPw);
        } else {
            llSetPw.setVisibility(View.VISIBLE);
            CommonUtils.autoSoftKeyboard(etPw1);
        }
        intent = getIntent();
        from = intent.getIntExtra(Constant.FROM, 0);
    }

    public void initView() {
        llSetPw = (LinearLayout) findViewById(R.id.ll_set_pw);
        etPw1 = (EditText) findViewById(R.id.et_pw1);
        etPw2 = (EditText) findViewById(R.id.et_pw2);
        llInputPw = (LinearLayout) findViewById(R.id.ll_input_pw);
        etPw = (EditText) findViewById(R.id.et_pw);
        btFinish = (Button) findViewById(R.id.bt_finish);
        btFinish.setOnClickListener(this);
        btIn = (Button) findViewById(R.id.bt_in);
        btIn.setOnClickListener(this);
    }

    public int getLayout() {
        return R.layout.ac_password;
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
                    if (from == 3) {
                        //来自MainActivity，修改密码
                        llInputPw.setVisibility(View.GONE);
                        llSetPw.setVisibility(View.VISIBLE);
                        CommonUtils.autoSoftKeyboard(etPw1);
                    } else if (from == 4) {
                        //来自SplashActivity，进入MainActivity
                        startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    ToastUtils.toastShort("密码输入错误");
                    etPw.setText("");
                }
                break;
            case R.id.bt_finish:
                String pw1 = etPw1.getText().toString();
                String pw2 = etPw2.getText().toString();
                if (TextUtils.isEmpty(pw1) || TextUtils.isEmpty(pw2)) {
                    ToastUtils.toastShort("密码不能为空");
                    break;
                }
                if (pw1.length() < 6 || pw2.length() < 6) {
                    ToastUtils.toastShort("密码不能少于6位");
                    break;
                }
                if (!pw1.equals(pw2)) {
                    ToastUtils.toastShort("两次密码输入不一致");
                    break;
                }
                SPUtils.putPW(pw1);
                SPUtils.putPWStatus(true);
                if (from == 3) {
                    finish();
                } else if (from == 4) {
                    startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                    finish();
                }
                break;
        }
    }

}
