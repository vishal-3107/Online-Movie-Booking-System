package com.user.micro.service;

import com.user.micro.service.validators.ValidatePassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;


@ValidatePassword
@Component
public class UserDetailsDto {

    @Size(min=3, max = 20, message = "Name must be between 3 and 20 characters")
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Mobile cannot be blank")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be valid and start with 6-9")
    private String mobileNo;

    @NotBlank(message = "Password is required")
    @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,15}$",
    message = "Password must be 8-15 characters long, include at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)"
            )
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    public @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters") @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters") @NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Mobile cannot be blank") @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be valid and start with 6-9") String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(@NotBlank(message = "Mobile cannot be blank") @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be valid and start with 6-9") String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public @NotBlank(message = "Password is required") @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,15}$",
            message = "Password must be 8-15 characters long, include at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)"
    ) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,15}$",
            message = "Password must be 8-15 characters long, include at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)"
    ) String password) {
        this.password = password;
    }

    public @NotBlank(message = "Confirm password is required") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "Confirm password is required") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
