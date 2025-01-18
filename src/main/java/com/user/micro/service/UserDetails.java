package com.user.micro.service;

import com.user.micro.service.validators.ValidatePassword;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

@Entity
@ValidatePassword
@Component
public class UserDetails {

    @Size(min=3, max = 20, message = "Name must be between 3 and 20 characters")
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Id
    @NotBlank(message = "Mobile cannot be blank")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be valid and start with 6-9")
    private String mobileNo;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,15}$",
            message = "Password must be 8-15 characters long, include at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)"
    )
    private String password;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,15}$",
            message = "Password must be 8-15 characters long, include at least one digit, one uppercase letter, one lowercase letter, and one special character (@#$%^&+=)"
    )


    private String confirmPassword;

    public UserDetails() {}

    public UserDetails(String confirmPassword, String password, String mobileNo, String email, String name) {
        this.confirmPassword = confirmPassword;
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
        this.name = name;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobileNo(String mobileNo) {
       this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
       return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
