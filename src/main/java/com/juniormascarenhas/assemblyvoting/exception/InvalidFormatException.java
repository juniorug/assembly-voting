package com.juniormascarenhas.assemblyvoting.exception;

public class InvalidFormatException extends PreconditionFailedException {

  private static final long serialVersionUID = 1749843540901937890L;

  public InvalidFormatException(String fieldName) {
    super(new MessageError(Messages.INVALID_FORMAT_ERROR, fieldName));
  }
}
