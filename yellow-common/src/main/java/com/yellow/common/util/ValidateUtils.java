package com.yellow.common.util;

import com.yellow.common.exception.ValidationParameterException;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

/**
 * hibernate validator的校验工具
 * @author zhouhao
 * @date  2021/3/26 9:30
 */
public final class ValidateUtils {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验实体类
     */
    public static <T> void validate(T t) {
        validate(result(t));
    }

    /**
     * 通过组来校验实体类
     */
    public static <T> void validate(T t, Class<?>... groups) {
        validate(result(t, groups));
    }

    /**
     * 通过组来校验实体类集合
     */
    public static <T> void validate(Collection<T> tCollection, Class<?>... groups) {
        if (CollectionUtils.isEmpty(tCollection)) {
            return;
        }

        for (T t : tCollection) {
            validate(t, groups);
        }
    }

    public static <T> List<ValidationParameterException.Parameter> parameters(T t) {
        return parameters(result(t));
    }

    public static <T> List<ValidationParameterException.Parameter> parameters(T t, Class<?>... groups) {
        return parameters(result(t, groups));
    }

    public static <T> List<ValidationParameterException.Parameter> parameters(Collection<T> tCollection, Class<?>... groups) {
        return parameters(result(tCollection, groups));
    }

    private static <T> void validate(Set<ConstraintViolation<T>> constraintViolations) {
        if (constraintViolations.size() > 0) {
            List<ValidationParameterException.Parameter> parameters = new ArrayList<>();
            for (ConstraintViolation<T> o : constraintViolations) {
                parameters.add(new ValidationParameterException.Parameter(o.getPropertyPath().toString(), o.getMessage()));
            }

            throw new ValidationParameterException(parameters);
        }
    }

    private static <T> Set<ConstraintViolation<T>> result(T t) {
        return VALIDATOR.validate(t);
    }

    private static <T> Set<ConstraintViolation<T>> result(T t, Class<?>... groups) {
        return VALIDATOR.validate(t, groups);
    }

    private static <T> Set<ConstraintViolation<T>> result(Collection<T> tCollection, Class<?>... groups) {
        if (CollectionUtils.isEmpty(tCollection)) {
            return null;
        }

        Set<ConstraintViolation<T>> result = new HashSet<>();
        for (T t : tCollection) {
            result.addAll(result(t, groups));
        }
        return result;
    }

    private static <T> List<ValidationParameterException.Parameter> parameters(Set<ConstraintViolation<T>> constraintViolations) {
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return null;
        }

        List<ValidationParameterException.Parameter> parameters = new ArrayList<>();
        for (ConstraintViolation<T> o : constraintViolations) {
            parameters.add(new ValidationParameterException.Parameter(o.getPropertyPath().toString(), o.getMessage()));
        }

        return parameters;
    }
}
