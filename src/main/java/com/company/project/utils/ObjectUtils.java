package com.company.project.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUtils {

    public static Map<String, String> getObjectFieldAndValues(Object o) {
        Map<String, String> fieldNameAndValue = new HashMap<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + fieldName.substring(1);
                Method method = o.getClass().getMethod(getter);
                Object value = method.invoke(o);
                if (value != null) {
                    fieldNameAndValue.put(fieldName, value.toString());
                }
            } catch (Exception e) {
                log.warn("get field value error.field:{},object:{}", field, o);
            }
        }
        return fieldNameAndValue;
    }
}
