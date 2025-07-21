package com.msnider.shortidgenerator.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.msnider.shortidgenerator.entity.Available;

@Service
public class AvailableService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<Available> findAll() {
    return this.mongoTemplate.findAll(Available.class);
  }

  public Optional<Available> findAndRemoveById(String id) {
    Query query = new Query(Criteria.where("id").is(id));
    Available removed = this.mongoTemplate.findAndRemove(query, Available.class);
    return Optional.ofNullable(removed);
  }

  public Collection<Available> saveAll(Collection<Available> available) {
    return this.mongoTemplate.insertAll(available);
  }
}
