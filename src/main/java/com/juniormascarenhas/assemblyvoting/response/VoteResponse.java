package com.juniormascarenhas.assemblyvoting.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.juniormascarenhas.assemblyvoting.config.ConfigConstants;
import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.enumeration.VoteValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFilter(ConfigConstants.FIELD_FILTER_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {

  private String id;

  private Associated associated;

  private TopicSession topicSession;

  private VoteValue value;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
