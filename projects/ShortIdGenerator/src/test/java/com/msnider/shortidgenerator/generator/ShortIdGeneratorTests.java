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
    long sequence = Long.MAX_VALUE;

    // Act
    String result = ShortIdGenerator.generate(sequence);

    // Assert
    assertEquals("wyVfWtYhZpm", result);
  }

  @Test
  public void testDecodeWithNull() throws Exception {
    // Arrange
    String shortId = null;

    // Act
    Exception exception = assertThrows(Exception.class, () -> {
      ShortIdGenerator.decode(shortId);
    });

    // Assert
    assertTrue(exception.getMessage().contains("Short id must be non-empty or null"));
  }

  @Test
  public void testDecodeWithEmpty() throws Exception {
    // Arrange
    String shortId = "";

    // Act
    Exception exception = assertThrows(Exception.class, () -> {
      ShortIdGenerator.decode(shortId);
    });

    // Assert
    assertTrue(exception.getMessage().contains("Short id must be non-empty or null"));
  }

  @Test
  public void testDecodeWithInvalidCharacter() throws Exception {
    // Arrange
    String shortId = "dqVOzeh";

    // Act
    Exception exception = assertThrows(Exception.class, () -> {
      ShortIdGenerator.decode(shortId);
    });

    // Assert
    assertTrue(exception.getMessage().contains("Invalid character in short id"));
  }

  @Test
  public void testDecodeWithZeroSequence() throws Exception {
    // Arrange
    String shortId = "aaaaaa";

    // Act
    long sequence = ShortIdGenerator.decode(shortId);

    // Assert
    assertEquals(0, sequence);
  }

  @Test
  public void testDecodeWithSmallSequence() throws Exception {
    // Arrange
    String shortId = "aaaabc";

    // Act
    long sequence = ShortIdGenerator.decode(shortId);

    // Assert
    assertEquals(60, sequence);
  }

  @Test
  public void testDecodeWithLargeSequence() throws Exception {
    // Arrange
    String shortId = "dqVzeh";

    // Act
    long sequence = ShortIdGenerator.decode(shortId);

    // Assert
    assertEquals(Integer.MAX_VALUE, sequence);
  }

@Test
  public void testDecodeWithMaxSequence() throws Exception {
    // Arrange
    String shortId = "wyVfWtYhZpm";

    // Act
    long sequence = ShortIdGenerator.decode(shortId);

    // Assert
    assertEquals(Long.MAX_VALUE, sequence);
  }
}
