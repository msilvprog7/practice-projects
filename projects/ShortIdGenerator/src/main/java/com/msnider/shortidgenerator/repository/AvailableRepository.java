package com.msnider.shortidgenerator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.msnider.shortidgenerator.entity.Available;

@Repository
public interface AvailableRepository extends MongoRepository<Available, String> {
  List<Available> findAll();
}
