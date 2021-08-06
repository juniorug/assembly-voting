package com.juniormascarenhas.assemblyvoting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juniormascarenhas.assemblyvoting.enumeration.AssociatedVoteStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

  @JsonProperty("status")
  private AssociatedVoteStatus status;

}
