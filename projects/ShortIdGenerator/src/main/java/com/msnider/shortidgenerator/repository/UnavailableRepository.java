package com.msnider.shortidgenerator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.msnider.shortidgenerator.entity.Unavailable;

@Repository
public interface UnavailableRepository extends MongoRepository<Unavailable, String> {
  List<Unavailable> findAll();
}
