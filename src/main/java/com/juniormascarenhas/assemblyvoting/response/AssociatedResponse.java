package com.juniormascarenhas.assemblyvoting.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.juniormascarenhas.assemblyvoting.config.ConfigConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFilter(ConfigConstants.FIELD_FILTER_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociatedResponse {

  private String id;

  private String name;

  private String cpf;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
