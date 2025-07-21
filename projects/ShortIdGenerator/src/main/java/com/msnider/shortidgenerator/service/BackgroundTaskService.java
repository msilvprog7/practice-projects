package com.msnider.shortidgenerator.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.msnider.shortidgenerator.entity.Available;
import com.msnider.shortidgenerator.generator.ShortIdGenerator;

@Service
public class BackgroundTaskService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundTaskService.class);
  private static final int MIN_AVAILABLE = 10;

  @Autowired
  private AvailableService availableService;

  @Autowired
  private SequenceService sequenceService;

  // One issue presented here is that by reading the database
  // to determine how many to generate, it's possible that
  // multiple tasks could write the same short ids (OK) but
  // could recreate ids that are taken (NOT OK) or exceed
  // capacity (NOT OK).
  @Scheduled(fixedRate = 5000)
  public void run() {
    LOGGER.info("Background task is running, time: {}", System.currentTimeMillis());

    List<Available> availableItems = this.availableService.findAll();
    LOGGER.info("Found {} items in available collection", availableItems.size());
    
    // Generate to keep minimum
    int toGenerate = Math.max(0, MIN_AVAILABLE - availableItems.size());
    if (toGenerate > 0) {
      try {
          // reserve sequence
          long startSequence = this.sequenceService.getNextSequenceBlock(toGenerate);
          LOGGER.info("Generating {} short ids, start sequence: {}", toGenerate, startSequence);

          // generate sequences
          List<Available> generatedItems = new ArrayList<>();

          for (int i = 0; i < toGenerate; i++) {
            long sequence = startSequence + i;
            String shortId = ShortIdGenerator.generate(sequence);
            generatedItems.add(new Available(shortId, System.currentTimeMillis()));
          }
          // save items second
          this.availableService.saveAll(generatedItems);
          LOGGER.info("Saved {} short ids", toGenerate);
      } catch (Exception e) {
        LOGGER.error("Failed to generate new short ids, exception: ", e);
      }
    }
  }
}
