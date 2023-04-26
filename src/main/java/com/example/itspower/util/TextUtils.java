package com.example.itspower.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

public class TextUtils {

    /* Đọc file từ hệ thống trả về byte[] */
    public static InputStream getFile(String pathname) {
        try {
            return new ClassPathResource(pathname).getInputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object getValue(Type type, Object value) {
        assert value != null;
        switch (type.getTypeName().toLowerCase()) {
            case "java.lang.double":
                return Double.valueOf(value.toString());
            case "java.lang.long":
                return Double.valueOf(value.toString()).longValue();
            case "java.lang.integer":
                return Double.valueOf(value.toString()).intValue();
            case "java.lang.string":
                return value.toString();
            case "java.util.date":
                return value.getClass().getTypeName().equals("java.util.Date") ? DateUtils.fromString(value.toString()) : value;
            default:
                return value;
        }
    }
}
