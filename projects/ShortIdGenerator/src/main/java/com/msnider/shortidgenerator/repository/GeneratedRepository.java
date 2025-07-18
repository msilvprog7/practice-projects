package com.msnider.shortidgenerator.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.msnider.shortidgenerator.entity.Generated;

@Repository
public interface GeneratedRepository extends MongoRepository<Generated, Long> {
  Optional<Generated> findTopByOrderByGeneratedAtDesc();
}
