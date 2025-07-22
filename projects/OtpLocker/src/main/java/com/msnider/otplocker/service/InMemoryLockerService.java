package com.msnider.otplocker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.msnider.otplocker.model.Locker;
import com.msnider.otplocker.model.Package;

@Service
public class InMemoryLockerService implements LockerService {

  @Override
  public Optional<Locker> reserve(Package dropOffPackage) {
    return Optional.ofNullable(new Locker("123456", 1.0, 2.0, 3.0));
  }

  @Override
  public Optional<Package> retrieve(String lockerId) {
    return Optional.ofNullable(new Package("123456", 1.0, 2.0, 3.0));
  }
  
}
