package com.juniormascarenhas.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1527545976609832638L;

  private final HttpStatus status;

  public NotFoundException() {
    this.status = HttpStatus.NOT_FOUND;
  }

  public NotFoundException(String message) {
    super(message);
    this.status = HttpStatus.NOT_FOUND;
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
    this.status = HttpStatus.NOT_FOUND;
  }

}
