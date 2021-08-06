package com.juniormascarenhas.assemblyvoting.enumeration;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortingFieldEnum {

  NAME("name"), CREATED_AT("createdAt"), REALIZATION_DATE("realizationDate");

  public final String label;

  public static SortingFieldEnum getSortingFieldByParam(String sortingField) {
    return Arrays.stream(SortingFieldEnum.values())
        .filter(sortingFieldEnum -> sortingFieldEnum.name().equals(sortingField)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException(sortingField));
  }
}
