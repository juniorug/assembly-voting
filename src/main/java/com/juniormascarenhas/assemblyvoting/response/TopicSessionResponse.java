package com.juniormascarenhas.assemblyvoting.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.juniormascarenhas.assemblyvoting.config.ConfigConstants;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.enumeration.SessionStatus;
import com.juniormascarenhas.assemblyvoting.enumeration.TopicResult;

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

  private SessionStatus status;

  private String assemblyId;

  private TopicResult topicResult;

  private int timeToBeOpen;

  private LocalDateTime dateTimeOpenned;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<Vote> votes;

}
