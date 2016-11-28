package com.example.handsomefu.passwordbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.handsomefu.passwordbook.Constant;
import com.example.handsomefu.passwordbook.R;
import com.example.handsomefu.passwordbook.bean.AccountItem;
import com.example.handsomefu.passwordbook.utils.CommonUtils;
import com.example.handsomefu.passwordbook.utils.SPUtils;
import com.google.gson.Gson;

/**
 * Created by HandsomeFu on 2016/10/25.
 */
public class AddActivity extends BaseActivity implements View.OnClickListener {
    private static final int EDIT = 2;
    private static final int ADD = 1;
    private EditText etType;
    private EditText etAccount;
    private EditText etPassword;
    private EditText etDes;
    private Button btAdd;
    private Intent intent;
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        from = intent.getIntExtra(Constant.FROM, 1);
        switch (from) {
            case 2:
                AccountItem accountItem = new Gson().fromJson(
                        intent.getStringExtra(Constant.ACCOUNT_ITEM), AccountItem.class);
                etType.setText(accountItem.getType());
                etAccount.setText(accountItem.getAccount());
                etPassword.setText(accountItem.getPassword());
                etDes.setText(accountItem.getDes());
                break;
        }
    }

    @Override
    public void initView() {
        etType = (EditText) findViewById(R.id.et_type);
        etAccount = (EditText) findViewById(R.id.et_account);
        CommonUtils.autoSoftKeyboard(etAccount);
        etPassword = (EditText) findViewById(R.id.et_password);
        etDes = (EditText) findViewById(R.id.et_des);
        btAdd = (Button) findViewById(R.id.bt_add);
        btAdd.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.ac_add;
    }

    public String getType() {
        return etType.getText().toString();
    }

    public String getAccount() {
        return etAccount.getText().toString();
    }

    public String getPassword() {
        return etPassword.getText().toString();
    }

    public String getDes() {
        return etDes.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add:
                AccountItem accountItem = new AccountItem();
                accountItem.setAccount(getAccount());
                accountItem.setDes(getDes());
                accountItem.setPassword(getPassword());
                accountItem.setType(getType());
                String s = new Gson().toJson(accountItem);
                switch (from) {
                    case 1:
                        //添加
                        SPUtils.putString(((SPUtils.getAmount() + 1)) + "", s);
                        //将新添加的条目传给MainActivity，在list中插入新条目
                        setResult(ADD, new Intent().putExtra(Constant.ACCOUNT_ITEM, s));
                        break;
                    case 2:
                        //修改
                        int position = intent.getIntExtra(Constant.POSITION, 0);
                        SPUtils.setString((position + 1) + "", s);
                        setResult(EDIT, null);
                        break;
                }
                finish();
                break;

        }
    }
}
