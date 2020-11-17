package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelRowException;
import com.github.cla9.excel.reader.factory.ReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Consumer;

public class ExcelTemplate{
    private final ReaderFactory readerFactory;

    public ExcelTemplate(ReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    public <T> ExcelResultSet<T> createResultSet(final Class<T> clazz, final MultipartFile multipartFile){
        return readerFactory.createInstance(clazz).createResultSet(multipartFile);
    }
    public <T> ExcelResultSet<T> createResultSet(final Class<T> clazz,final MultipartFile multipartFile, final boolean forceMergedHeaderRefresh){
        return readerFactory.createInstance(clazz).createResultSet(multipartFile, forceMergedHeaderRefresh);
    }
    public <T> void parse(final Class<T> clazz, final  MultipartFile multipartFile,
               final Consumer<T> onSuccessConsumer,
               final Consumer<ExcelRowException> onErrorConsumer){
        readerFactory.createInstance(clazz).parse(multipartFile, onSuccessConsumer, onErrorConsumer);
    }
    public <T> void parse(final Class<T> clazz, final  MultipartFile multipartFile,
               final Consumer<T> onSuccessConsumer,
               final Consumer<ExcelRowException> onErrorConsumer, final boolean forceMergedHeaderRefresh){
        readerFactory.createInstance(clazz).parse(multipartFile, onSuccessConsumer, onErrorConsumer, forceMergedHeaderRefresh);
    }
}
