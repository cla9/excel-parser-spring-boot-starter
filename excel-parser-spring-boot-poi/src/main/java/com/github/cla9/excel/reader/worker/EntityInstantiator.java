package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ErrorInfo;
import com.github.cla9.excel.reader.entity.ErrorType;
import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.row.RowHandler;
import com.github.cla9.excel.reader.util.ExcelBeanUtil;
import com.github.drapostolos.typeparser.TypeParser;
import com.github.drapostolos.typeparser.TypeParserException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * The type Entity instantiator.
 *
 * @param <T> the type parameter
 */
public class EntityInstantiator<T> {
    private final Validator validator;
    private final TypeParser typeParser;
    private List<ErrorInfo> exceptions;
    private List<EntityOrder> instances;
    private int iterator = 0;

    /**
     * Instantiates a new Entity instantiator.
     */
    public EntityInstantiator() {
        typeParser = TypeParser.newBuilder().build();
        validator = ExcelBeanUtil.getBean(LocalValidatorFactoryBean.class).getValidator();
        exceptions = new ArrayList<>();
        instances = new ArrayList<>();
    }

    /**
     * Create instance entity injection result.
     *
     * @param <R>              the type parameter
     * @param clazz            the clazz
     * @param excelHeaderNames the excel header names
     * @param excelMetaModel   the excel meta model
     * @param rowHandler       the row handler
     * @return the entity injection result
     */
    public <R> EntityInjectionResult<T> createInstance(Class<? extends T> clazz, List<String> excelHeaderNames, ExcelMetaModel excelMetaModel, RowHandler<R> rowHandler) {
        resourceCleanUp();
        final T object = BeanUtils.instantiateClass(clazz);

        ReflectionUtils.doWithFields(clazz, f -> {
            if (!excelMetaModel.isPartialParseOperation()) {
                instantiateFullInjectionObject(object, excelHeaderNames, f, rowHandler);
            } else if (excelMetaModel.getInstantiatorSource().isCandidate(f)) {
                instantiatePartialInjectionObject(object, excelHeaderNames, excelMetaModel, f);
            }
        });

        if (excelMetaModel.isPartialParseOperation()) {
            setupInstance(excelHeaderNames, rowHandler);
        }

        return new EntityInjectionResult<>(object, List.copyOf(exceptions));
    }

    private <U> void setupInstance(final List<? extends String> headers, final RowHandler<U> rowHandler) {
        for (int i = 0; i < instances.size(); i++) {
            if (Objects.isNull(instances.get(i))) continue;

            Field field = instances.get(i).field;
            field.setAccessible(true);
            String value = rowHandler.getValue(i);

            try {
                final Object instance = instances.get(i).instance;
                if (!StringUtils.isEmpty(value)) {
                    field.set(instance, typeParser.parseType(value, field.getType()));
                }
                validate(instance, headers.get(i), value, field.getName()).ifPresent(exceptions::add);
            } catch (IllegalAccessException e) {
                addException(headers, field, value, e.getLocalizedMessage());
            }

        }
    }


    /**
     * Resource clean up.
     */
    public void resourceCleanUp() {
        iterator = 0;
        exceptions.clear();
        instances.clear();
    }

    private <U, R> void instantiateFullInjectionObject(final U instance, final List<? extends String> headers, final Field field, final RowHandler<R> rowHandler) {
        if (iterator == headers.size()) return;

        final Class<?> type = field.getType();
        field.setAccessible(true);

        if (!ClassUtils.isPrimitiveOrWrapper(type) && !String.class.isAssignableFrom(type)) {
            final Object newInstance = BeanUtils.instantiateClass(type);
            try {
                field.set(instance, newInstance);
            } catch (IllegalAccessException e) {
                addException(headers, field, null, e.getLocalizedMessage());
            }
            ReflectionUtils.doWithFields(type, f -> instantiateFullInjectionObject(newInstance, headers, f, rowHandler));
        } else {
            final String value = rowHandler.getValue(iterator);
            if (!isEmpty(value)) {
                try {
                    field.set(instance, typeParser.parseType(value, field.getType()));
                } catch (TypeParserException | IllegalAccessException e) {
                    addException(headers, field, value, e.getLocalizedMessage());
                }
            }
            validate(instance, headers.get(iterator++), value, field.getName()).ifPresent(exceptions::add);
        }
    }

    private <U> void instantiatePartialInjectionObject(final U instance, final List<? extends String> headers, ExcelMetaModel metadata, final Field field) {
        final Class<?> type = field.getType();
        field.setAccessible(true);

        final EntityInstantiatorSource instanceSource = metadata.getInstantiatorSource();
        if (instanceSource.isCreationTargetField(field)) {
            final Object newInstance = BeanUtils.instantiateClass(type);
            try {
                field.set(instance, newInstance);
            } catch (IllegalAccessException e) {
                addException(headers, field, null, e.getLocalizedMessage());
            }
            ReflectionUtils.doWithFields(type, f -> {
                if (instanceSource.isCandidate(f)) {
                    instantiatePartialInjectionObject(newInstance, headers, metadata, f);
                }
            });
        } else if (instanceSource.isInjectionFields(field)) {
            instances.add(new EntityOrder(instance, field));
        }
    }

    private void addException(final List<? extends String> headers, final Field field, final String value, final String message) {
        ErrorType error = ErrorType.UNKNOWN;
        exceptions.add(ErrorInfo.builder()
                .type(error.name())
                .field(field.getName())
                .fieldHeader(headers.get(iterator))
                .value(value)
                .errorMessages(List.of(message))
                .build());
    }


    private <U> Optional<ErrorInfo> validate(final U object, final String fieldHeader, final String cellValue, final String fieldName) {

        final List<ConstraintViolation<U>> violations = validator.validate(object)
                .stream()
                .filter(d -> d.getPropertyPath().toString().equals(fieldName))
                .collect(Collectors.toList());

        if (violations.size() < 1) return Optional.empty();

        ErrorType error = ErrorType.VALID;

        return Optional.ofNullable(ErrorInfo.builder()
                .type(error.name())
                .field(violations.get(0).getPropertyPath().toString())
                .fieldHeader(fieldHeader)
                .value(cellValue)
                .errorMessages(violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()))
                .build());
    }

    /**
     * The type Entity order.
     */
    static class EntityOrder {
        /**
         * The Instance.
         */
        Object instance;
        /**
         * The Field.
         */
        Field field;

        /**
         * Instantiates a new Entity order.
         *
         * @param instance the instance
         * @param field    the field
         */
        public EntityOrder(Object instance, Field field) {
            this.instance = instance;
            this.field = field;
        }
    }
}
