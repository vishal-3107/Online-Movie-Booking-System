package com.user.micro.service;

import com.twilio.Twilio;
//import com.twilio.rest.conversations.v1.conversation.Message;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.http.HttpResponse;
import com.user.micro.service.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final JavaMailSender mailSender;
    private final UserDetails user;

    public UserService(UserDao userDao, TwilioConfig twilioConfig, JavaMailSender mailSender, UserDetails user) {
        this.userDao = userDao;
        this.mailSender = mailSender;
        this.user = user;
    }

    public UserDetails createUserDetails(UserDetails userDetails) {
        if (userDao.existsByEmail(userDetails.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        String otp = OTPGenerator.generateOTP();
        System.out.println("I am printing otp: " + otp);
        sendOtpToEmail(userDetails.getEmail(), otp);
//        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
        sendOtpToPhone();
        System.out.println("sent phone otp successfully");
        if (userDao.existsByMobileNo(userDetails.getMobileNo())) {
            throw new IllegalArgumentException("Mobile Number Exists");
        }

        userDao.save(userDetails);
        return userDetails;
    }

    private void sendOtpToEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vishalkumarsingh028@gmail.com");
        message.setTo(email); // Recipient's email
        message.setSubject("Your OTP Code"); // Subject of the email
        message.setText("Dear User,\n\nYour OTP code is: " + otp +
                "\n\nPlease use this code to verify your email. This OTP is valid for 5 minutes.\n\nRegards,\nTeam");
        mailSender.send(message); // Send the email
        System.out.println("OTP email sent to: " + email);
    }



    private void sendOtpToPhone() {
        try {
            // Generate OTP
            String phoneOtp = OTPGenerator.generateOTP();
            String apiKey = "jSksJEbwZpX2zHYylM3tr0eQo85qKvUVhuC1d4FIfcA9NDxL6my96qJtmM5iDYnE7SwVQN3KdkrRFOvc"; // Your API key
            String message = "Hey Banik, Whether you are feeling cold?"; // Message to be sent
            String language = "english"; // Language of the message
            String route = "q"; // Route as per documentation
            List<String> phoneNumber = List.of("7632858309");
            String numbers = String.join(",", phoneNumber);

            // URL-encode the message to ensure special characters are handled correctly
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

            // API URL
            String url = String.format("https://www.fast2sms.com/dev/bulkV2?authorization=%s&message=%s&language=%s&route=%s&numbers=%s",
                    apiKey, encodedMessage, language, route, numbers);

            // Create RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Set headers (if any additional headers are needed)
            HttpHeaders headers = new HttpHeaders();
            headers.add("cache-control", "no-cache");

            // Create the HTTP request
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Execute the request
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Handle the response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("OTP sent successfully: " + response.getBody());
            } else {
                System.err.println("Failed to send OTP. Status: " + response.getStatusCode() + ", Response: " + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("An error occurred while sending OTP: " + e.getMessage());
        }
    }

}
