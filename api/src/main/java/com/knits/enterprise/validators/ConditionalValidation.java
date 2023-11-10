package com.knits.enterprise.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConditionalValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalValidation {
    String message() default "Field is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values();

    String[] requiredProperties();

    String conditionalProperty();
}
