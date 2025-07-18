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

  public static long decode(String shortId) throws Exception {
    // handle when null or empty
    if (shortId == null || shortId.length() == 0) {
      throw new Exception("Short id must be non-empty or null");
    }

    // decode character by character, in reverse
    long sequence = 0;

    for (int i = 0; i < shortId.length(); i++) {
      Character current = shortId.charAt(i);
      int index = CHARACTERS.indexOf(current);

      if (index == -1) {
        throw new Exception("Invalid character in short id, short id: " + shortId + ", i: " + i + ", current: " + current);
      }

      sequence *= BASE;
      sequence += index;
    }

    // return value
    return sequence;
  }
}
