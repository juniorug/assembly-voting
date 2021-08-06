package com.juniormascarenhas.assemblyvoting.exception;

import java.util.List;

public class InvalidOpenSessionException extends PreconditionFailedException {

  private static final long serialVersionUID = -5594788757630429770L;

  public InvalidOpenSessionException(List<MessageError> errors) {
    super(errors);
  }

}
