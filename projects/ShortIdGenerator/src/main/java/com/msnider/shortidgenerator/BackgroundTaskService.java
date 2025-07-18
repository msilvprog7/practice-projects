package com.msnider.shortidgenerator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.msnider.shortidgenerator.entity.Available;
import com.msnider.shortidgenerator.entity.Generated;
import com.msnider.shortidgenerator.generator.ShortIdGenerator;
import com.msnider.shortidgenerator.repository.AvailableRepository;
import com.msnider.shortidgenerator.repository.GeneratedRepository;

@Service
public class BackgroundTaskService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundTaskService.class);
  private static final int MIN_AVAILABLE = 100;

  @Autowired
  private AvailableRepository availableRepository;

  @Autowired
  private GeneratedRepository generatedRepository;

  // One issue presented here is that by reading the database
  // to determine how many to generate, it's possible that
  // multiple tasks could write the same short ids (OK) but
  // could recreate ids that are taken (NOT OK) or exceed
  // capacity (NOT OK).
  @Scheduled(fixedRate = 5000)
  public void run() {
    LOGGER.info("Background task is running, time: {}", System.currentTimeMillis());

    List<Available> availableItems = this.availableRepository.findAll();
    LOGGER.info("Found {} items in available collection", availableItems.size());

    Generated latestGenerated = this.generatedRepository.findTopByOrderByGeneratedAtDesc().orElse(new Generated(-1, -1));
    LOGGER.info("Latest sequence {}", latestGenerated.getLatestSequence());
    
    // Generate to keep minimum
    int toGenerate = Math.max(0, MIN_AVAILABLE - availableItems.size());
    if (toGenerate > 0) {
      LOGGER.info("Generating {} short ids, latest sequence: {}", toGenerate, latestGenerated.getLatestSequence());

      try {
          List<Available> generatedItems = new ArrayList<>();
          long latestSequence = latestGenerated.getLatestSequence() + 1;
          long nextLatestSequence = latestSequence + toGenerate;

          for (long sequence = latestSequence; sequence <= nextLatestSequence; sequence++) {
            String shortId = ShortIdGenerator.generate(sequence);
            generatedItems.add(new Available(shortId, System.currentTimeMillis()));
          }

          // save generated entry first
          this.generatedRepository.save(new Generated(nextLatestSequence, System.currentTimeMillis()));
          LOGGER.info("Generated {} short ids, updated latest sequence {}", toGenerate, nextLatestSequence);

          // save items second
          availableRepository.saveAll(generatedItems);
          LOGGER.info("Saved {} short ids", toGenerate);
      } catch (Exception e) {
        LOGGER.error("Failed to generate new short ids, exception: ", e);
      }
    }
  }
}
