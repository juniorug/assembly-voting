package com.juniormascarenhas.assemblyvoting.exception;

import java.util.List;

public class InvalidParamException extends PreconditionFailedException {

  private static final long serialVersionUID = 8787641583537163547L;

  public InvalidParamException(List<MessageError> errors) {
    super(errors);
  }

}
