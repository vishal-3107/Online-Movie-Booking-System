package com.user.micro.service.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidatePassword {

    public String message() default "Password didn't match ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
