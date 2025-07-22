package com.msnider.otplocker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class InMemoryOtpService implements OtpService<String> {

  @Override
  public String lock(String value) {
    return "123456";
  }

  @Override
  public Optional<String> unlock(String otp) {
    return null;
  }
  
}
