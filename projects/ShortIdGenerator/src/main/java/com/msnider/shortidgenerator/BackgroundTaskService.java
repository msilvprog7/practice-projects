package com.msnider.shortidgenerator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.msnider.shortidgenerator.entity.Available;
import com.msnider.shortidgenerator.repository.AvailableRepository;

@Service
public class BackgroundTaskService {
  private static final Logger logger = LoggerFactory.getLogger(BackgroundTaskService.class);

  @Autowired
  private AvailableRepository availableRepository;

  @Scheduled(fixedRate = 5000)
  public void run() {
    logger.info("Background task is running, time: {}", System.currentTimeMillis());

    List<Available> availableItems = availableRepository.findAll();
    logger.info("Found {} items in available collection", availableItems.size());

    List<Available> generatedItems = new ArrayList<>();
    generatedItems.add(new Available("0", System.currentTimeMillis()));
    availableRepository.saveAll(generatedItems);
  }
}
