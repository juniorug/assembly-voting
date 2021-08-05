package com.juniormascarenhas.assemblyvoting.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Size;

import com.juniormascarenhas.assemblyvoting.entity.Assembly;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.entity.Vote;
import com.juniormascarenhas.assemblyvoting.enumeration.SessionStatus;
import com.juniormascarenhas.assemblyvoting.exception.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicSessionRequest {

  @Size(max = 1024, message = Messages.SIZE_1024_INVALID_LENGTH_ERROR)
  private String name;

  @Size(max = 1024, message = Messages.SIZE_1024_INVALID_LENGTH_ERROR)
  private String description;

  private String assemblyId;

  private long timeToBeOpen;

  private List<Vote> votes;

  public TopicSession toEntity(Assembly assembly) {
    return TopicSession.builder()
        .createdAt(LocalDateTime.now())
        .name(name)
        .description(description)
        .assembly(assembly)
        .status(SessionStatus.CREATED)
        .timeToBeOpen(timeToBeOpen)
        .build();
  }

  public TopicSession toEntity(TopicSession topicSession, Assembly assembly) {
    return TopicSession.builder()
        .id(topicSession.getId())
        .createdAt(topicSession.getCreatedAt())
        .updatedAt(LocalDateTime.now())
        .name(name)
        .description(description)
        .assembly(assembly)
        .timeToBeOpen(timeToBeOpen)
        .votes(topicSession.getVotes())
        .build();
  }

}
