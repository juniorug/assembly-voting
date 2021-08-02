package com.juniormascarenhas.assemblyvoting.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.juniormascarenhas.assemblyvoting.config.ConfigConstants;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFilter(ConfigConstants.FIELD_FILTER_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssemblyResponse {

  private String id;

  private String description;

  private LocalDateTime realizationDate;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<TopicSession> topicSessions;

}
