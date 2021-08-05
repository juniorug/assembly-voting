package com.juniormascarenhas.assemblyvoting.request;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import com.juniormascarenhas.assemblyvoting.entity.Associated;
import com.juniormascarenhas.assemblyvoting.entity.TopicSession;
import com.juniormascarenhas.assemblyvoting.exception.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociatedRequest {

  @Size(max = 1024, message = Messages.SIZE_137_INVALID_LENGTH_ERROR)
  private String name;

  @Size(max = 1024, message = Messages.SIZE_11_INVALID_LENGTH_ERROR)
  private String cpf;

  public Associated toEntity() {
    return Associated.builder()
        .createdAt(LocalDateTime.now())
        .name(name)
        .cpf(cpf)
        .build();
  }

  public Associated toEntity(TopicSession topicSession, Associated associated) {
    return Associated.builder()
        .id(associated.getId())
        .cpf(associated.getCpf())
        .updatedAt(LocalDateTime.now())
        .name(name)
        .build();
  }
}
