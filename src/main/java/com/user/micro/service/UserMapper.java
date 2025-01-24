package com.user.micro.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserDetails user;

    public UserMapper(UserDetails user)
    {
        this.user = user;
    }

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDetails toEntity(UserDetailsDto dto)
    {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setMobileNo(dto.getMobileNo());
        return user;
    }
}
