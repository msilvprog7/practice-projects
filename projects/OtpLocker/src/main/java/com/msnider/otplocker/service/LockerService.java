package com.msnider.otplocker.service;

import java.util.Optional;

import com.msnider.otplocker.model.Locker;
import com.msnider.otplocker.model.Package;

public interface LockerService {
  /**
   * Reserve a locker that can fit the package
   * and store the package in the locker.
   * @param dropOffPackage Package to store.
   * @return Locker, optional if there is room.
   */
  Optional<Locker> reserve(Package dropOffPackage);

  /**
   * Retrieves a package from a locker id if
   * there is a package stored there.
   * @param lockerId Id of the locker to retrieve from.
   * @return Package, optional if there is a package.
   */
  Optional<Package> retrieve(String lockerId);
}
