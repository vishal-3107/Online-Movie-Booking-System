package com.user.micro.service.validators;

import com.user.micro.service.Dto.UserDetailsDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidatePassword, UserDetailsDto> {

    @Override
    public boolean isValid(UserDetailsDto userDetails, ConstraintValidatorContext constraintValidatorContext) {
        return userDetails.getPassword().equals(userDetails.getConfirmPassword());
    }
}
