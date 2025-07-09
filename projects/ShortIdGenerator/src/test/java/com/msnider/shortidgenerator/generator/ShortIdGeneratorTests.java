package com.msnider.shortidgenerator.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ShortIdGeneratorTests {
  @Test
  public void testGenerateWithNegativeSequence() throws Exception {
    // Arrange
    long sequence = -1;

    // Act
    Exception exception = assertThrows(Exception.class, () -> {
      ShortIdGenerator.generate(sequence);
    });

    // Assert
    assertTrue(exception.getMessage().contains("Sequence must be non-negative"));
  }

  @Test
  public void testGenerateWithZeroSequence() throws Exception {
    // Arrange
    long sequence = 0;

    // Act
    String result = ShortIdGenerator.generate(sequence);

    // Assert
    assertEquals("aaaaaa", result);
  }

  @Test
  public void testGenerateWithSmallSequence() throws Exception {
    // Arrange
    long sequence = 60;

    // Act
    String result = ShortIdGenerator.generate(sequence);

    // Assert
    assertEquals("aaaabc", result);
  }

  @Test
  public void testGenerateWithLargeSequence() throws Exception {
    // Arrange
    long sequence = (long)Integer.MAX_VALUE;

    // Act
    String result = ShortIdGenerator.generate(sequence);

    // Assert
    assertEquals("dqVzeh", result);
  }

  @Test
  public void testGenerateWithMaxSequence() throws Exception {
    // Arrange
    long sequence = Long.MAX_VALUE - 1;

    // Act
    String result = ShortIdGenerator.generate(sequence);

    // Assert
    assertEquals("wyVfWtYhZpk", result);
  }
}
