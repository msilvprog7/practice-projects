package com.msnider.otplocker.controller;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.msnider.otplocker.dto.DropOffPackage;
import com.msnider.otplocker.dto.PickUpId;
import com.msnider.otplocker.dto.Response;
import com.msnider.otplocker.model.Dimensions;
import com.msnider.otplocker.model.Locker;
import com.msnider.otplocker.model.Package;
import com.msnider.otplocker.service.LockerService;
import com.msnider.otplocker.service.OtpService;

@ExtendWith(MockitoExtension.class)
public class OtpLockerControllerTests {
  
  @Mock
  private LockerService lockerService;

  @Mock
  private OtpService<String> otpService;

  @InjectMocks
  private OtpLockerController controller;
  
  @Test
  public void testDropOff_NoCapacity() throws Exception {
    // Arrange
    DropOffPackage dropOffPackage = new DropOffPackage();

    when(this.lockerService.reserve(any(Package.class)))
      .thenReturn(Optional.empty());

    // Act
    ResponseEntity<Response<PickUpId>> response = this.controller.dropOff(dropOffPackage);

    // Assert
    assertEquals(409, response.getStatusCode().value());

    Response<PickUpId> responseBody = response.getBody();
    assertNotNull(responseBody);
    assertEquals("There is not enough capacity for your package.", responseBody.getMessage());
  }

  @Test
  public void testDropOff_Created() throws Exception {
    // Arrange
    DropOffPackage dropOffPackage = new DropOffPackage();
    Locker locker = new Locker("Locker-1", new Dimensions(1.0, 2.0, 3.0));
    String otp = "123456";

    when(this.lockerService.reserve(any(Package.class)))
      .thenReturn(Optional.ofNullable(locker));
    when(this.otpService.lock(locker.getId()))
      .thenReturn(otp);

    // Act
    ResponseEntity<Response<PickUpId>> response = this.controller.dropOff(dropOffPackage);

    // Assert
    assertEquals(201, response.getStatusCode().value());
    
    Response<PickUpId> responseBody = response.getBody();
    assertNotNull(responseBody);
    
    PickUpId value = responseBody.getValue();
    assertNotNull(value);
    assertEquals(otp, value.getOtp());
  }

  @Test
  public void testPickUp_NoOtp() throws Exception {
    // Arrange
    PickUpId pickUpId = new PickUpId("123456");

    when(this.otpService.unlock(pickUpId.getOtp()))
      .thenReturn(Optional.empty());

    // Act
    ResponseEntity<Response<DropOffPackage>> response = this.controller.pickUp(pickUpId);

    // Assert
    assertEquals(401, response.getStatusCode().value());

    Response<DropOffPackage> responseBody = response.getBody();
    assertNotNull(responseBody);
    assertEquals("There is no package found for your package.", responseBody.getMessage());
  }

  @Test
  public void testPickUp_NoPackage() throws Exception {
    // Arrange
    PickUpId pickUpId = new PickUpId("123456");
    Locker locker = new Locker("Locker-1", new Dimensions(1.0, 2.0, 3.0));
    locker.setDropOffPackage(null);

    when(this.otpService.unlock(pickUpId.getOtp()))
      .thenReturn(Optional.ofNullable(locker.getId()));
    when(this.lockerService.retrieve(locker.getId()))
      .thenReturn(Optional.empty());

    // Act
    ResponseEntity<Response<DropOffPackage>> response = this.controller.pickUp(pickUpId);

    // Assert
    assertEquals(404, response.getStatusCode().value());

    Response<DropOffPackage> responseBody = response.getBody();
    assertNotNull(responseBody);
    assertEquals("There was no package stored in the locker.", responseBody.getMessage());
  }

  @Test
  public void testPickUp_Success() throws Exception {
    // Arrange
    PickUpId pickUpId = new PickUpId("123456");
    Locker locker = new Locker("Locker-1", new Dimensions(1.0, 2.0, 3.0));
    locker.setDropOffPackage(new Package("1", new Dimensions(3.0, 2.0, 1.0)));

    when(this.otpService.unlock(pickUpId.getOtp()))
      .thenReturn(Optional.ofNullable(locker.getId()));
    when(this.lockerService.retrieve(locker.getId()))
      .thenReturn(Optional.ofNullable(locker.getDropOffPackage()));

    // Act
    ResponseEntity<Response<DropOffPackage>> response = this.controller.pickUp(pickUpId);

    // Assert
    assertEquals(200, response.getStatusCode().value());

    Response<DropOffPackage> responseBody = response.getBody();
    assertNotNull(responseBody);

    DropOffPackage value = responseBody.getValue();
    assertNotNull(value);
    assertEquals("1", value.getId());
    assertEquals(3.0, value.getWidth());
    assertEquals(2.0, value.getHeight());
    assertEquals(1.0, value.getDepth());
  }

}