package com.user.micro.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao  extends JpaRepository<UserDetails, Integer> {
    boolean existsByEmail(String email);

    boolean existsByMobileNo(String mobileNo);
}
