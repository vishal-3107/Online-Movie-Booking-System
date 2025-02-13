package com.user.micro.service.Controller;

import com.user.micro.service.Service.UserService;
import com.user.micro.service.Dto.UserDetailsDto;
import com.user.micro.service.otpvalidator.OtpValidationRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/register")
    public String registerUserDetails( @RequestBody @Valid UserDetailsDto userDetails)
    {
        String user = userService.createUserDetails(userDetails);
        return user;
    }

    @PostMapping("/otpValidate")
    public String validateUser(@RequestBody OtpValidationRequest otpRequest)
    {
        String isValid = userService.validateOtp(otpRequest);
        return isValid;
    }

}
