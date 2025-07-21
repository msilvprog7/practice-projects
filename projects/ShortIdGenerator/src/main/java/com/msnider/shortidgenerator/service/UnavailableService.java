package com.msnider.shortidgenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.msnider.shortidgenerator.entity.Unavailable;

@Service
public class UnavailableService {
  
  @Autowired
  private MongoTemplate mongoTemplate;

  public List<Unavailable> findAll() {
    return this.mongoTemplate.findAll(Unavailable.class);
  }

  public Unavailable save(Unavailable unavailable) {
    return this.mongoTemplate.save(unavailable);
  }
}
