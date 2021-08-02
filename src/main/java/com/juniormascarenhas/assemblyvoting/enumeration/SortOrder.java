package com.juniormascarenhas.assemblyvoting.enumeration;

import java.util.Arrays;

import org.springframework.data.domain.Sort;

public enum SortOrder {

  ASC {
    @Override
    public Sort getSortBy() {
      return Sort.by(Sort.Order.asc(REALIZATION_DATE));
    }

    @Override
    public Sort getSortBy(String fieldName) {
      return Sort.by(Sort.Order.asc(fieldName));
    }
  },
  DESC {
    @Override
    public Sort getSortBy() {
      return Sort.by(Sort.Order.desc(REALIZATION_DATE));
    }

    @Override
    public Sort getSortBy(String fieldName) {
      return Sort.by(Sort.Order.desc(fieldName));
    }

  };

  private static final String REALIZATION_DATE = "realizationDate";

  public abstract Sort getSortBy();

  public static SortOrder getSortByParam(String sort) {
    return Arrays.stream(SortOrder.values()).filter(sortOrder -> sortOrder.name().equals(sort)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException(sort));
  }

  public abstract Sort getSortBy(String fieldName);

}
