package com.msnider.otplocker.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.msnider.otplocker.model.Dimensions;
import com.msnider.otplocker.model.Locker;
import com.msnider.otplocker.model.Package;

import jakarta.annotation.PostConstruct;

@Service
public class RedisLockerService implements LockerService {

  private final String prefix;
  private final String intializedId;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  public RedisLockerService() {
    this.prefix = "locker:";
    this.intializedId = "initialized";
  }

  @PostConstruct
  public void initializeLockers() {
    String key = this.getKey(this.intializedId);
    if (this.redisTemplate.hasKey(key)) {
      return;
    }

    // setup 5 small lockers to test, this should be handled elsewhere and separate from service
    for (int i = 1; i <= 5; i++) {
      Dimensions dimensions = new Dimensions(4.0, 4.0, 4.0);
      Locker locker = new Locker("Locker-" + i, dimensions);
      this.redisTemplate.opsForValue().set(this.getKey(locker.getId()), locker);
    }

    this.redisTemplate.opsForValue().set(key, "true");
  }

  @Override
  public Optional<Locker> reserve(Package dropOffPackage) {
    Set<String> keys = this.redisTemplate.keys(this.getKey("*"));
    String initializedKey = this.getKey(this.intializedId);

    for (String key : keys) {
      if (key.equals(initializedKey)) {
        continue;
      }

      Locker locker = (Locker) this.redisTemplate.opsForValue().get(key);
      if (locker != null && locker.dropOffPackage(dropOffPackage)) {
        this.redisTemplate.opsForValue().set(key, locker);
        return Optional.ofNullable(locker);
      }
    }

    return Optional.empty();
  }

  @Override
  public Optional<Package> retrieve(String lockerId) {
    String key = this.getKey(lockerId);
    Locker locker = (Locker) this.redisTemplate.opsForValue().get(key);
    if (locker == null) {
      return Optional.empty();
    }

    Optional<Package> pickUpPackage = locker.pickUpPackage();
    this.redisTemplate.opsForValue().set(key, locker);
    return pickUpPackage;
  }

  private String getKey(String id) {
    return this.prefix + id;
  }
  
}
