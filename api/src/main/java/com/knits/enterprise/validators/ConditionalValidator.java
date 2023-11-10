package com.knits.enterprise.validators;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Slf4j
public class ConditionalValidator implements ConstraintValidator<ConditionalValidation, Object> {

    private String conditionalProperty;
    private String[] requiredProperties;
    private String message;
    private String[] values;

    @Override
    public void initialize(ConditionalValidation constraint) {
        conditionalProperty = constraint.conditionalProperty();
        requiredProperties = constraint.requiredProperties();
        message = constraint.message();
        values = constraint.values();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        log.info("Validating value {}", object);
        try {
            Object conditionalPropertyValue = String.valueOf(getPropertyValue(object, conditionalProperty));
            log.info("Property value {}", conditionalPropertyValue);
            if (doConditionalValidation(conditionalPropertyValue)) {
                return validateRequiredProperties(object, context);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            return false;
        }
        return true;
    }

    private boolean validateRequiredProperties(Object object, ConstraintValidatorContext context) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        boolean isValid = true;
        for (String property : requiredProperties) {
            Object requiredValue = getPropertyValue(object, property);
            boolean isPresent = requiredValue != null && !isEmpty(requiredValue);
            if (!isPresent) {
                isValid = false;
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(property)
                        .addConstraintViolation();
            }
        }
        return isValid;
    }

    private boolean doConditionalValidation(Object actualValue) {
        return Arrays.asList(values).contains(actualValue);
    }

    private Object getPropertyValue(Object object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = object.getClass();
        try {
            Field field = clazz.getDeclaredField(propertyName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException e) {
            throw new NoSuchMethodException("No such field: " + propertyName);
        }
    }

    private boolean isEmpty(Object value) {
        return value instanceof String && ((String) value).isEmpty();
    }
}
