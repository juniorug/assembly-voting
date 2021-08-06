package com.juniormascarenhas.assemblyvoting.enumeration;

import java.util.stream.Stream;

public enum TopicResult {
  APPROVED, REJECTED, DRAW;

  public static TopicResult getByValue(String value) {
    return Stream.of(values()).filter(v -> v.name().equals(value)).findFirst().orElse(null);
  }
}
