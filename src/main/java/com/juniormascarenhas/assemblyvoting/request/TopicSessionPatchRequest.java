package com.juniormascarenhas.assemblyvoting.request;

import javax.validation.constraints.NotBlank;

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

  @NotBlank(message = Messages.FIELD_REQUIRED_ERROR)
  private SessionStatus status;

}
