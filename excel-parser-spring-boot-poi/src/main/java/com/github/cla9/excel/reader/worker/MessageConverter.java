package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.message.DefaultMessageRepository;
import com.github.cla9.excel.reader.message.ExcelMessageRepository;
import com.github.cla9.excel.reader.util.ExcelBeanUtil;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.Objects;

public class MessageConverter {
    private final ExcelMessageRepository messageRepository;

    public MessageConverter(Class<? extends ExcelMessageRepository> source) {
        this.messageRepository = this.findMessageRepository(source);
    }

    private ExcelMessageRepository findMessageRepository(Class<? extends ExcelMessageRepository> source) {
        if (!DefaultMessageRepository.class.equals(source)) {
            return ExcelBeanUtil.getBean(source);
        } else {
            try {
                return ExcelBeanUtil.getBean(ExcelMessageRepository.class);
            } catch (NoSuchBeanDefinitionException var3) {
                return null;
            }
        }
    }

    public String convertMessage(String code) {
        return Objects.isNull(this.messageRepository) ? code : this.messageRepository.getMessage(code);
    }

    public static MessageConverter.MessageConverterBuilder builder() {
        return new MessageConverter.MessageConverterBuilder();
    }

    public static class MessageConverterBuilder {
        private Class<? extends ExcelMessageRepository> source;

        public MessageConverter.MessageConverterBuilder source(final Class<? extends ExcelMessageRepository> source) {
            this.source = source;
            return this;
        }
        public MessageConverter build() {
            return new MessageConverter(this.source);
        }
    }
}