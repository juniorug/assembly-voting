package com.juniormascarenhas.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ServiceUnavailableException extends RuntimeException {

  private static final long serialVersionUID = -8310287465011301791L;

  private final HttpStatus status;

  public ServiceUnavailableException() {
    this.status = HttpStatus.SERVICE_UNAVAILABLE;
  }

}
