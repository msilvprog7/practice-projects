package com.msnider.shortidgenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.msnider.shortidgenerator.entity.Sequence;

@Service
public class SequenceService {
  private static final int START_SEQUENCE = 0;
  
  @Autowired
  private MongoTemplate mongoTemplate;

  public long getNextSequenceBlock(int blockSize) {
    // setup query to update sequence
    Query query = new Query(Criteria.where("id").is(Sequence.SEQUENCE_ID));
    Update update = new Update()
      .inc("currentSequence", blockSize)
      .set("lastUpdated", System.currentTimeMillis());
    FindAndModifyOptions options = FindAndModifyOptions.options()
      .returnNew(false)
      .upsert(true);

    // modify sequence and return value
    Sequence sequence = this.mongoTemplate.findAndModify(
      query,
      update,
      options,
      Sequence.class);
    return sequence != null ? sequence.getCurrentSequence() : START_SEQUENCE;
  }
}
