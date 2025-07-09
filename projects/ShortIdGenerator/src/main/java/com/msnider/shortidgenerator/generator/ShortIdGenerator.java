package com.msnider.shortidgenerator.generator;

public class ShortIdGenerator {
  private static final String CHARACTERS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789";
  private static final int BASE = CHARACTERS.length();
  private static final int MIN_LENGTH = 6;

  public static String generate(long sequence) throws Exception {
    if (sequence < 0) {
      throw new Exception("Sequence must be non-negative, sequence: " + sequence);
    }

    StringBuilder sb = new StringBuilder();

    while (sequence > 0) {
      int index = (int)(sequence % BASE);
      sb.append(CHARACTERS.substring(index, index + 1));
      sequence /= BASE;
    }

    while (sb.length() < MIN_LENGTH) {
      sb.append(CHARACTERS.substring(0, 1));
    }

    return sb.reverse().toString();
  }
}
