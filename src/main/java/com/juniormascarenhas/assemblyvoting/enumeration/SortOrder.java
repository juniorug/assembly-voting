package com.juniormascarenhas.assemblyvoting.enumeration;

import java.util.Arrays;

import org.springframework.data.domain.Sort;

public enum SortOrder {

  ASC {
    @Override
    public Sort getSortBy() {
      return Sort.by(Sort.Order.asc(CREATED_AT));
    }

    @Override
    public Sort getSortBy(String fieldName) {
      return Sort.by(Sort.Order.asc(fieldName));
    }
  },
  DESC {
    @Override
    public Sort getSortBy() {
      return Sort.by(Sort.Order.desc(CREATED_AT));
    }

    @Override
    public Sort getSortBy(String fieldName) {
      return Sort.by(Sort.Order.desc(fieldName));
    }

  };

  private static final String CREATED_AT = "createdAt";

  public abstract Sort getSortBy();

  public static SortOrder getSortByParam(String sort) {
    return Arrays.stream(SortOrder.values()).filter(sortOrder -> sortOrder.name().equals(sort)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException(sort));
  }

  public abstract Sort getSortBy(String fieldName);

}
