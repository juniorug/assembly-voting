package com.juniormascarenhas.assemblyvoting.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PreconditionFailedException extends RuntimeException {

  private static final long serialVersionUID = 2570307977763927366L;

  private final HttpStatus status;
  private final List<MessageError> errors;

  public PreconditionFailedException(MessageError error) {
    this.status = HttpStatus.PRECONDITION_FAILED;
    this.errors = List.of(error);
  }

  public PreconditionFailedException(List<MessageError> errors) {
    this.status = HttpStatus.PRECONDITION_FAILED;
    this.errors = errors;
  }

}
