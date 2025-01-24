package com.user.micro.service.otpvalidator;

public class OtpValidationRequest {

    private String emailOtp;
    private String mobileOtp;

    public OtpValidationRequest(String emailOtp, String mobileOtp) {
        this.emailOtp = emailOtp;
        this.mobileOtp = mobileOtp;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public void setEmailOtp(String emailOtp) {
        this.emailOtp = emailOtp;
    }

    public String getMobileOtp() {
        return mobileOtp;
    }

    public void setMobileOtp(String mobileOtp) {
        this.mobileOtp = mobileOtp;
    }

    @Override
    public String toString() {
        return "OtpValidationRequest{" +
                "emailOtp='" + emailOtp + '\'' +
                ", mobileOtp='" + mobileOtp + '\'' +
                '}';
    }
}
