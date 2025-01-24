package com.user.micro.service.otpvalidator;

import java.security.SecureRandom;

public class OTPGenerator {
    public static String generateOTP() {
        SecureRandom secureRandom = new SecureRandom();
        int otp = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(otp);
    }
}

