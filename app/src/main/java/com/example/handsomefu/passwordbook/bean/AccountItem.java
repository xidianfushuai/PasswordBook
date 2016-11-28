package com.example.handsomefu.passwordbook.bean;

/**
 * Created by HandsomeFu on 2016/10/25.
 */
public class AccountItem {
    private String type;
    private String account;
    private String password;
    private String des;

    public void setType(String type) {
        this.type = type;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getDes() {
        return des;
    }
}
