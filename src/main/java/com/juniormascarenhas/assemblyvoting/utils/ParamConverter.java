package com.juniormascarenhas.assemblyvoting.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamConverter {

  private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  public static Integer convertLimit(String limit) {
    if (!StringUtils.isNumeric(limit) || Integer.parseInt(limit) < 1) {
      throw new IllegalArgumentException(limit);
    }
    return Integer.valueOf(limit);
  }

  public static Integer convertOffset(String number) {
    if (!StringUtils.isNumeric(number)) {
      throw new IllegalArgumentException(number);
    }
    return Integer.valueOf(number);
  }

  public static LocalDateTime convertDate(String date) {
    try {
      return LocalDateTime.parse(date, DATE_TIME_PATTERN);
    } catch (Exception e) {
      throw new IllegalArgumentException(date);
    }
  }
}
