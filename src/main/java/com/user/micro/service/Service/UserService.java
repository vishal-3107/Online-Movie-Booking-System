package com.user.micro.service.Service;

import com.user.micro.service.Repository.UserDao;
import com.user.micro.service.Dto.UserDetailsDto;
import com.user.micro.service.Dto.UserMapper;
import com.user.micro.service.otpvalidator.OtpService;
import com.user.micro.service.otpvalidator.OtpValidationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final OtpService otpService;
    private final UserDetailsDto userDetailsDto;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String phoneOtp = null;
    private String emailOtp = null;


    public UserService(UserDao userDao, UserMapper userMapper, OtpService otpService, UserDetailsDto userDetailsDto) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.otpService = otpService;
        this.userDetailsDto = userDetailsDto;
    }

    public String createUserDetails(UserDetailsDto userDetailsDto) {
        if (userDao.existsByEmail(userDetailsDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userDao.existsByMobileNo(userDetailsDto.getMobileNo())) {
            throw new IllegalArgumentException("Mobile Number Exists");
        }
        String storedEmailOtp = otpService.sendOtpToEmail(userDetailsDto.getEmail());
        String storedPhoneOtp = otpService.sendOtpToPhone(userDetailsDto.getMobileNo());
        redisTemplate.opsForValue().set("userDetailsCache:" + storedEmailOtp, userDetailsDto, 120, TimeUnit.SECONDS);
        phoneOtp = storedPhoneOtp;
        emailOtp = storedEmailOtp;
        return "OTP has been sent to email and phone";
    }

    public String validateOtp(OtpValidationRequest otpValidationRequest) {
        String validateEmailOtp = otpValidationRequest.getEmailOtp();
        String validatePhoneOtp = otpValidationRequest.getMobileOtp();
        String key = "userDetailsCache:"+validateEmailOtp;
        if (key == null || key.isEmpty()) {
            throw new RuntimeException("User details not found in Redis ");
        }
        Object cachedValue = redisTemplate.opsForValue().get(key);
        UserDetailsDto dataFromRedis = (UserDetailsDto) cachedValue;
        boolean isValid = validatePhoneOtp.equals(phoneOtp) && validateEmailOtp.equals(emailOtp);
        if (isValid) {
            userDao.save(userMapper.toEntity((Objects.requireNonNull(dataFromRedis))));
            return "OTP is validated and Data Saved successfully in db";
        }
           else {
            return "Either your email otp or phone otp is not valid";
        }
    }

}



