package com.msnider.otplocker.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisOtpService implements OtpService<String> {

  private final Random random;
  private final int length;
  private final String prefix;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  public RedisOtpService() {
    this.random = new Random();
    this.length = 6;
    this.prefix = "otp:";
  }
  
  @Override
  public String lock(String value) {
    // this has a problem, should throw an exception if it cannot be generated
    String otp = null;
    do { 
        otp = this.generateOtp();
    } while (this.redisTemplate.opsForValue().get(this.getKey(otp)) != null);

    // store the locker id
    this.redisTemplate.opsForValue().set(this.getKey(otp), value);
    return otp;
  }

  @Override
  public Optional<String> unlock(String otp) {
    String key = this.getKey(otp);
    String lockerId = this.redisTemplate.opsForValue().get(key);
    if (lockerId == null) {
      return Optional.empty();
    }

    this.redisTemplate.delete(key);
    return Optional.ofNullable(lockerId);
  }

  private String getKey(String otp) {
    return this.prefix + otp;
  }

  private String generateOtp() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < this.length; i++) {
      sb.append(this.random.nextInt(1, 10));
    }

    return sb.toString();
  }
  
}
