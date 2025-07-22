package com.msnider.otplocker.dto;

public class PickUpId {
  private String otp;

  public PickUpId(String otp) {
    this.otp = otp;
  }

  public String getOtp() {
    return this.otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }
}
