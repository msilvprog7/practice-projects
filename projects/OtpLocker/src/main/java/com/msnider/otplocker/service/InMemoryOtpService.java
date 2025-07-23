package com.msnider.otplocker.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class InMemoryOtpService implements OtpService<String> {

  private final Map<String, String> otpsToLockers;
  private final Random random;
  private final int length;

  public InMemoryOtpService() {
    this.otpsToLockers = new HashMap<>();
    this.random = new Random();
    this.length = 6;
  }
  
  @Override
  public String lock(String value) {
    // this has a problem, should throw an exception if it cannot be generated
    String otp = null;
    do { 
        otp = this.generateOtp();
    } while (this.otpsToLockers.containsKey(otp));

    // store the locker id
    this.otpsToLockers.put(otp, value);
    return otp;
  }

  @Override
  public Optional<String> unlock(String otp) {
    if (!this.otpsToLockers.containsKey(otp)) {
      return Optional.empty();
    }

    return Optional.ofNullable(this.otpsToLockers.remove(otp));
  }

  private String generateOtp() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < this.length; i++) {
      sb.append(this.random.nextInt(1, 10));
    }

    return sb.toString();
  }
  
}
