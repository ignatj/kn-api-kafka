package com.knits.enterprise.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateTimeFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateTimeFormat {
    String format();

    String message() default "Time format wrong or out of bounds. Please use {format}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
