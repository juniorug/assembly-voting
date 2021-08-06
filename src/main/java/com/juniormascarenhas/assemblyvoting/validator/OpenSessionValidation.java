package com.juniormascarenhas.assemblyvoting.validator;

import com.juniormascarenhas.assemblyvoting.enumeration.SessionStatus;
import com.juniormascarenhas.assemblyvoting.exception.ChangeSessionStatusException;
import com.juniormascarenhas.assemblyvoting.exception.MessageError;
import com.juniormascarenhas.assemblyvoting.exception.Messages;
import com.juniormascarenhas.assemblyvoting.exception.PreconditionFailedException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenSessionValidation {

  public static void validate(SessionStatus currentState, SessionStatus newState) {

    if (newState != SessionStatus.OPENED) {
      throw new PreconditionFailedException(new MessageError(Messages.STATUS_RESQUESTED_MUST_BE_OPENED));
    }

    if (currentState != SessionStatus.OPENED) {
      throw new ChangeSessionStatusException(Messages.SESSION_ALREADY_OPENED);
    } else if (currentState != SessionStatus.CLOSED) {
      throw new ChangeSessionStatusException(Messages.SESSION_ALREADY_CLOSED);
    }

  }

}
