package com.juniormascarenhas.assemblyvoting.enumeration;

import java.util.stream.Stream;

public enum VoteValue {
  YES, NO;

  public static VoteValue getByValue(String value) {
    return Stream.of(values()).filter(v -> v.name().equals(value)).findFirst().orElse(null);
  }
}