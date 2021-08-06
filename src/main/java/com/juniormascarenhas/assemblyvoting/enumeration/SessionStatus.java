package com.juniormascarenhas.assemblyvoting.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SessionStatus {
  CREATED, OPENED, CLOSED;

  public static List<String> getPropertyValues() {
    return Arrays.stream(values()).map(SessionStatus::name).collect(Collectors.toList());
  }

}
