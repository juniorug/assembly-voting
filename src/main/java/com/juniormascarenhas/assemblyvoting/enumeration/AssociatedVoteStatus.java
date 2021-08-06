package com.juniormascarenhas.assemblyvoting.enumeration;

import java.util.stream.Stream;

//@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AssociatedVoteStatus {
  ABLE_TO_VOTE, UNABLE_TO_VOTE;

  public static AssociatedVoteStatus getByValue(String value) {
    return Stream.of(values()).filter(v -> v.name().equals(value)).findFirst().orElse(null);
  }
}