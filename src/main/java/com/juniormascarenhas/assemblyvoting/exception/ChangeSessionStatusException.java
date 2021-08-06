package com.juniormascarenhas.assemblyvoting.exception;

public class ChangeSessionStatusException extends UnprocessableEntityException {

  private static final long serialVersionUID = 2597679033056838021L;

  public ChangeSessionStatusException(String message) {
    super(message);
  }

}
