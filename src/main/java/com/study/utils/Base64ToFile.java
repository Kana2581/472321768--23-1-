package com.study.utils;

import java.util.Base64;

public class Base64ToFile {
    public static String extractLastString(String url)
    {
        int lastIndex = url.lastIndexOf('/');

        // Extract the substring after the last '/'\
        System.out.println(url.substring(lastIndex + 1));
        return url.substring(lastIndex + 1);
    }
    public static int Base64Split(String base64Str)
    {

        return base64Str.indexOf(",");

    }
    public static byte[] convert(String base64Str)
    {
        System.out.println(base64Str.substring(Base64Split(base64Str)));
        return Base64.getDecoder().decode(base64Str.substring(Base64Split(base64Str)+1));
    }
}
