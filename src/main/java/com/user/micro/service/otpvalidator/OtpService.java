package com.user.micro.service.otpvalidator;

import com.user.micro.service.UserDetailsDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class OtpService {

    private final JavaMailSender mailSender;
    private final UserDetailsDto userDetailsDto;

    public OtpService(JavaMailSender mailSender, UserDetailsDto userDetailsDto) {
        this.mailSender = mailSender;
        this.userDetailsDto = userDetailsDto;
    }


    public String sendOtpToEmail(String email) {
        String emailOtp = OTPGenerator.generateOTP();
        System.out.println("Email OTP Generated: " + emailOtp);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vishalkumarsingh028@gmail.com");
        message.setTo(email); // Recipient's email
        message.setSubject("Your OTP Code"); // Subject of the email
        message.setText("Dear User,\n\nYour OTP code is: " + emailOtp +
                "\n\nPlease use this code to verify your email. This OTP is valid for 5 minutes.\n\nRegards,\nTeam");
        mailSender.send(message); // Send the email
        System.out.println("OTP email sent to: " + email);
        return emailOtp;
    }

    public String sendOtpToPhone(String userPhone) {
        String phoneOtp = OTPGenerator.generateOTP();
        try {
            System.out.println("Phone OTP Generated: " + phoneOtp);
            String apiKey = "jSksJEbwZpX2zHYylM3tr0eQo85qKvUVhuC1d4FIfcA9NDxL6my96qJtmM5iDYnE7SwVQN3KdkrRFOvc"; // Your API key
            String message = "Hey, your OTP is:" + phoneOtp; // Message to be sent
            String language = "english"; // Language of the message
            String route = "q"; // Route as per documentation
            List<String> phoneNumber = List.of(userPhone);
            String numbers = String.join(",", phoneNumber);
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

            String url = String.format("https://www.fast2sms.com/dev/bulkV2?authorization=%s&message=%s&language=%s&route=%s&numbers=%s",
                    apiKey, encodedMessage, language, route, numbers);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("cache-control", "no-cache");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(" Phone OTP sent successfully: " + response.getBody());
            } else {
                System.err.println("Failed to send Phone OTP. Status: " + response.getStatusCode() + ", Response: " + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("An error occurred while sending OTP: " + e.getMessage());
        }

        return phoneOtp;

    }
}
