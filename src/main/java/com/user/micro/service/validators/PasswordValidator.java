package com.user.micro.service.validators;

import com.user.micro.service.UserDetails;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidatePassword, UserDetails> {

    @Override
    public boolean isValid(UserDetails userDetails, ConstraintValidatorContext constraintValidatorContext) {
        return userDetails.getPassword().equals(userDetails.getConfirmPassword());
    }
}
