package com.msnider.otplocker.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msnider.otplocker.dto.DropOffPackage;
import com.msnider.otplocker.dto.PickUpId;
import com.msnider.otplocker.dto.Response;
import com.msnider.otplocker.model.Locker;
import com.msnider.otplocker.model.Package;
import com.msnider.otplocker.service.LockerService;
import com.msnider.otplocker.service.OtpService;

@RestController
@RequestMapping("/api/lockers/v1")
public class OtpLockerController {
  
  @Autowired
  private LockerService lockerService;
  
  @Autowired
  private OtpService<String> otpService;

  @PostMapping("/dropoff")
  public ResponseEntity<Response<PickUpId>> dropOff(@RequestBody DropOffPackage dropOffPackage) {
    // reserve locker for package
    Optional<Locker> lockerResult = this.lockerService.reserve(new Package(dropOffPackage));
    if (lockerResult.isEmpty()) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(Response.error("There is not enough capacity for your package."));
    }

    // store otp for locker
    Locker locker = lockerResult.get();
    String otp = this.otpService.lock(locker.getId());

    // return otp in pickup response
    PickUpId pickUpId = new PickUpId(otp);
    return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(pickUpId));
  }

  @PostMapping("/pickup")
  public ResponseEntity<Response<DropOffPackage>> pickUp(@RequestBody PickUpId pickUpId) {
    // check the otp
    Optional<String> lockerIdResult = this.otpService.unlock(pickUpId.getOtp());
    if (lockerIdResult.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error("There is no package found for your package."));
    }

    // release the package
    String lockerId = lockerIdResult.get();
    Optional<Package> dropOffPackageResult = this.lockerService.retrieve(lockerId);
    if (dropOffPackageResult.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("There was no package stored in the locker."));
    }

    // return the package
    DropOffPackage dropOffPackage = new DropOffPackage(dropOffPackageResult.get());
    return ResponseEntity.ok(Response.success(dropOffPackage));
  }
}
