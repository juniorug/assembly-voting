package com.juniormascarenhas.assemblyvoting.request;

import javax.validation.constraints.NotNull;

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
public class TopicSessionPatchRequest {

  @NotNull(message = Messages.FIELD_REQUIRED_ERROR)
  private SessionStatus status;

}
