package com.github.cla9.excel.reader.util;

import org.springframework.beans.factory.BeanFactory;


/**
 * The type Excel bean util.
 */
public class ExcelBeanUtil {
    private static BeanFactory factory;

    /**
     * Gets bean.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return the bean
     */
    public static <T> T getBean(Class<T> type) {
        return factory.getBean(type);
    }
}
