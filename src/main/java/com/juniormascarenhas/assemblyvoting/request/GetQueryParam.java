package com.juniormascarenhas.assemblyvoting.request;

import com.juniormascarenhas.assemblyvoting.enumeration.SortOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetQueryParam {

  private Integer limit;

  private Integer offset;

  private String keywords;

  private SortOrder sort;

}
