package com.github.cla9.excel.reader.util;

import org.springframework.beans.factory.BeanFactory;



public class ExcelBeanUtil {
    private static BeanFactory factory;
    public static <T> T getBean(Class<T> type) {
        return factory.getBean(type);
    }
}
