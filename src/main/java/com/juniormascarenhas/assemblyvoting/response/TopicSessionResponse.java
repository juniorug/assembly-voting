package com.juniormascarenhas.assemblyvoting.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.juniormascarenhas.assemblyvoting.config.ConfigConstants;
import com.juniormascarenhas.assemblyvoting.entity.Vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFilter(ConfigConstants.FIELD_FILTER_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicSessionResponse {

  private String id;

  private String name;

  private String description;

  private String status;

  private String assemblyId;

  private String topicResultId;

  private long timeToBeOpen;

  private LocalDateTime timeOpenned;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<Vote> votes;

}
