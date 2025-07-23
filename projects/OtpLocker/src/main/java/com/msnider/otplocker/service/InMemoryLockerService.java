package com.msnider.otplocker.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.msnider.otplocker.model.Dimensions;
import com.msnider.otplocker.model.Locker;
import com.msnider.otplocker.model.Package;

@Service
public class InMemoryLockerService implements LockerService {

  private final Map<String, Locker> lockers;

  public InMemoryLockerService() {
    this.lockers = new HashMap<>();

    // Setup 5 small lockers to test
    for (int i = 1; i <= 5; i++) {
      Dimensions dimensions = new Dimensions(4.0, 4.0, 4.0);
      Locker locker = new Locker("Locker-" + i, dimensions);
      lockers.put(locker.getId(), locker);
    }
  }

  @Override
  public Optional<Locker> reserve(Package dropOffPackage) {
    for (Locker locker : this.lockers.values()) {
      if (locker.dropOffPackage(dropOffPackage)) {
        return Optional.ofNullable(locker);
      }
    }

    return Optional.empty();
  }

  @Override
  public Optional<Package> retrieve(String lockerId) {
    if (!this.lockers.containsKey(lockerId)) {
      return Optional.empty();
    }

    return this.lockers.get(lockerId).pickUpPackage();
  }
  
}
