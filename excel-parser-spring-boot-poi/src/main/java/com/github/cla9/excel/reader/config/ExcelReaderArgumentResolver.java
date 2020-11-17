package com.github.cla9.excel.reader.config;

import com.github.cla9.excel.reader.annotation.ExcelRequestBody;
import com.github.cla9.excel.reader.worker.ExcelResultSet;

import com.github.cla9.excel.reader.worker.ExcelTemplate;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class ExcelReaderArgumentResolver implements HandlerMethodArgumentResolver {
    private final ExcelTemplate excelTemplate;

    public ExcelReaderArgumentResolver(final ExcelTemplate excelTemplate) {
        this.excelTemplate = excelTemplate;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final boolean hasExcelFileAnnotation = !Objects.isNull(parameter.getParameterAnnotation(ExcelRequestBody.class));
        final boolean isExcelResultSetParam = ExcelResultSet.class.isAssignableFrom(parameter.getParameterType());
        return hasExcelFileAnnotation && isExcelResultSetParam;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        final MultipartHttpServletRequest request = webRequest.getNativeRequest(MultipartHttpServletRequest.class);
        final ExcelRequestBody header = parameter.getParameterAnnotation(ExcelRequestBody.class);
        assert request != null;
        assert header != null;
        final MultipartFile file = request.getFile(header.name());
        final Type actualTypeArgument = ((ParameterizedType) parameter.getGenericParameterType()).getActualTypeArguments()[0];
        final Class<?> classType = (Class<?>) actualTypeArgument;
        return excelTemplate.createResultSet(classType,file, header.mergeHeaderCheck());
    }
}
