package com.example.handsomefu.passwordbook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.handsomefu.passwordbook.Constant;
import com.example.handsomefu.passwordbook.MyApplication;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HandsomeFu on 2016/10/26.
 */
public class SPUtils {
    private static final String SP_NAME = "password_list";
    private static final String OTHER_FLAG = "other_flag";
    private static final String AMOUNT = "amount";
    private static final SharedPreferences sharedPreferences = MyApplication.getContext().
            getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    private static final SharedPreferences.Editor editor = sharedPreferences.edit();
    private static final SharedPreferences sharedPreferencesFlags = MyApplication.getContext().
            getSharedPreferences(OTHER_FLAG, Context.MODE_PRIVATE);
    private static final SharedPreferences.Editor editorFlag = sharedPreferencesFlags.edit();
    private static final String IF_SETED = "ifSeted";
    private static final String PASSWORD = "passwoed";
    private static int amount;

    public static void putString(String key, String object) {
        editor.putString(key, object);
        editor.commit();
        amountPP();
    }

    public static void setString(String key, String object) {

        editor.putString(key, object);
        editor.commit();
    }

    public static String getString(String key) {
        String str = sharedPreferences.getString(key, null);
        System.out.println("str1:" + str);
        if (!TextUtils.isEmpty(str)) {
            char[] c = str.toCharArray();
            for (int i = 0; i < c.length; i++) {
                System.out.print((int)c[i] + "  ");
            }
        }

        return str;
    }

    public static void putAmount(int i) {
        editor.putInt(AMOUNT, i);
        editor.commit();
    }

    public static int getAmount() {
        return sharedPreferences.getInt(AMOUNT, 0);
    }

    public static void amountPP() {
        amount = sharedPreferences.getInt(AMOUNT, 0);
        editor.putInt(AMOUNT, ++amount);
        editor.commit();
    }

    public static void amountSS() {
        amount = sharedPreferences.getInt(AMOUNT, 0);
        editor.putInt(AMOUNT, --amount);
        editor.commit();
    }

    public static void clear() {
        editor.clear();
        editor.commit();
    }

    public static void putPWStatus(boolean b) {
        editorFlag.putBoolean(IF_SETED, b);
        editorFlag.commit();
    }

    public static boolean getPWStatus() {
        return sharedPreferencesFlags.getBoolean(IF_SETED, false);
    }

    public static void putPW(String pw) {
        try {
            pw = HexUtil.getEncryptedPwd(pw);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        editorFlag.putString(PASSWORD, pw);
        editorFlag.commit();
    }

    public static String getPW() {
        return sharedPreferencesFlags.getString(PASSWORD, null);
    }
}
