package com.getir.readingisgood.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {
    @Autowired
    PasswordEncoder encoder;

    public String encodePassword(String password) {
        return encoder.encode(password);
    }
}
