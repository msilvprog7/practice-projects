package com.msnider.otplocker.service;

import java.util.Optional;

public interface OtpService<T> {
  /**
   * Stores the value and generates an OTP for the value.
   * @param value Id or value to store
   * @return One-time passcode
   */
  String lock(T value);

  /**
   * Looks up the value to remove and return, if stored.
   * @param otp One-time passcode
   * @return Optional Id or value
   */
  Optional<T> unlock(String otp);
}
