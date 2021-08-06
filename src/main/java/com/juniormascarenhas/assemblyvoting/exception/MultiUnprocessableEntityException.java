package com.juniormascarenhas.assemblyvoting.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class MultiUnprocessableEntityException extends RuntimeException {

  private static final long serialVersionUID = 5120698653021961500L;

  private final HttpStatus status;
  private final List<MessageError> errors;

  public MultiUnprocessableEntityException(List<MessageError> errors) {
    this.status = HttpStatus.UNPROCESSABLE_ENTITY;
    this.errors = errors;
  }

}