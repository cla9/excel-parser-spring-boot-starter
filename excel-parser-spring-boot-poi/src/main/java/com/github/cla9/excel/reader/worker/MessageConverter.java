package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.message.DefaultMessageRepository;
import com.github.cla9.excel.reader.message.ExcelMessageRepository;
import com.github.cla9.excel.reader.util.ExcelBeanUtil;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.Objects;

/**
 * The type Message converter.
 */
public class MessageConverter {
    private final ExcelMessageRepository messageRepository;

    /**
     * Instantiates a new Message converter.
     *
     * @param source the source
     */
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

    /**
     * Convert message string.
     *
     * @param code the code
     * @return the string
     */
    public String convertMessage(String code) {
        return Objects.isNull(this.messageRepository) ? code : this.messageRepository.getMessage(code);
    }

    /**
     * Builder message converter . message converter builder.
     *
     * @return the message converter . message converter builder
     */
    public static MessageConverter.MessageConverterBuilder builder() {
        return new MessageConverter.MessageConverterBuilder();
    }

    /**
     * The type Message converter builder.
     */
    public static class MessageConverterBuilder {
        private Class<? extends ExcelMessageRepository> source;

        /**
         * Source message converter . message converter builder.
         *
         * @param source the source
         * @return the message converter . message converter builder
         */
        public MessageConverter.MessageConverterBuilder source(final Class<? extends ExcelMessageRepository> source) {
            this.source = source;
            return this;
        }

        /**
         * Build message converter.
         *
         * @return the message converter
         */
        public MessageConverter build() {
            return new MessageConverter(this.source);
        }
    }
}