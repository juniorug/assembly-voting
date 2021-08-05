package com.juniormascarenhas.assemblyvoting.request;

import java.time.LocalDateTime;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.enumeration.VoteValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {

  private Associated associated;

  private TopicSession topicSession;

  private VoteValue value;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
  

  public Vote toEntity(Associated associated, TopicSession topicSession) {
    return Vote.builder()
        .createdAt(LocalDateTime.now())
        .associated(associated)
        .topicSession(topicSession)
        .build();
  }

  public Vote toEntity(Vote vote) {
    return Vote.builder()
        .id(vote.getId())
        .createdAt(vote.getCreatedAt())
        .updatedAt(LocalDateTime.now())
        .associated(vote.getAssociated())
        .topicSession(vote.getTopicSession())
        .build();
  }

}
