package com.study.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 私有构造函数，防止外部实例化
    private JsonUtils() {
    }

    // 将Java对象转换为JSON字符串的方法
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace(); // 处理异常，这里简化为打印堆栈信息
            return null;
        }
    }
}