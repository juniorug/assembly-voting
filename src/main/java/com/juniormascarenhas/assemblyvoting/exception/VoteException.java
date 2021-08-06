package com.juniormascarenhas.assemblyvoting.exception;

import java.util.List;

public class VoteException extends UnprocessableEntityException {

  private static final long serialVersionUID = 46029972480125017L;

  public VoteException(String code, List<MessageError> errors) {
    super(code, errors);
  }
}
