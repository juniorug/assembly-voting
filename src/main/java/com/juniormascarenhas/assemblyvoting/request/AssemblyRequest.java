package com.juniormascarenhas.assemblyvoting.request;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juniormascarenhas.assemblyvoting.entity.Assembly;
import com.juniormascarenhas.assemblyvoting.exception.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssemblyRequest {

  @Size(max = 1024, message = Messages.SIZE_1024_INVALID_LENGTH_ERROR)
  private String description;

  @JsonProperty("realizationDate")
  @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
  private LocalDateTime realizationDate;

  public Assembly toEntity() {
    return Assembly.builder().createdAt(LocalDateTime.now()).description(description).realizationDate(realizationDate)
        .build();
  }

  public Assembly toEntity(Assembly assembly) {
    return Assembly.builder().id(assembly.getId()).createdAt(assembly.getCreatedAt()).updatedAt(LocalDateTime.now())
        .description(description).realizationDate(realizationDate).topicSessions(assembly.getTopicSessions()).build();
  }
}
