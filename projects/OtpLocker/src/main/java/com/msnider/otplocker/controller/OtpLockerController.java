package com.msnider.otplocker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msnider.otplocker.dto.DropOffPackage;
import com.msnider.otplocker.dto.PickUpId;
import com.msnider.otplocker.service.OtpService;

@RestController
@RequestMapping("/api/lockers/v1")
public class OtpLockerController {
  @Autowired
  private OtpService<String> otpService;

  @PostMapping
  public ResponseEntity<PickUpId> dropOff(@RequestBody DropOffPackage dropOffPackage) {
    // todo: reserve locker for package
    // store otp for locker
    String otp = this.otpService.lock("locker id");

    // return otp in pickup response
    PickUpId pickUpId = new PickUpId(otp);
    return ResponseEntity.ok(pickUpId);
  }

  @GetMapping
  public ResponseEntity<String> pickUp(@RequestBody PickUpId pickUpId) {
    return ResponseEntity.ok().build();
  }
}
