package com.example.handsomefu.passwordbook.utils;

/**
 * Created by HandsomeFu on 2016/10/27.
 */
public class MyEncryptionUtils {
    public static String encrypt(String string) {
        char[] c = string.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            long l = (long) c[i];
            String s = String.valueOf(l);
            s = format(s);
            sb.append(s);
        }
        System.out.println(sb.toString());
        System.out.println("decode: " + decode(sb.toString()));
        return sb.toString();
    }

    private static String format(String s) {
        StringBuilder sb = null;
        switch (s.length()) {
            case 1:
                sb = new StringBuilder("0000000");
                sb.append(s);
                break;
            case 2:
                sb = new StringBuilder("000000");
                sb.append(s);
                break;
            case 3:
                sb = new StringBuilder("00000");
                sb.append(s);
                break;
            case 4:
                sb = new StringBuilder("0000");
                sb.append(s);
                break;
            case 5:
                sb = new StringBuilder("000");
                sb.append(s);
                break;
            case 6:
                sb = new StringBuilder("00");
                sb.append(s);
                break;
            case 7:
                sb = new StringBuilder("0");
                sb.append(s);
                break;
        }
        return sb.toString();
    }

    public static String decode(String s) {
        boolean prepared = false;
        int start = 0;
        int end = 0;
        String temp;
        StringBuilder sb = new StringBuilder();
        char c;
        char cc;
        int in;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (!(c == '0')) {
                prepared = true;
                end = i;
            }else {
                if (prepared) {
                    temp = s.substring(start + 1, end + 1);
                    in = (Integer.parseInt(temp));
                    cc = (char)in;
                    sb.append(cc);
                    prepared = false;
                }
                start = i;
            }
        }
        return sb.toString();
    }

}
