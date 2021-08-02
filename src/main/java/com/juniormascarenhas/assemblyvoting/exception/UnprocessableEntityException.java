package com.juniormascarenhas.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UnprocessableEntityException extends RuntimeException {

  private static final long serialVersionUID = 5120698653021961500L;

  private final String code;
  private final transient Object[] args;
  private final HttpStatus status;

  public UnprocessableEntityException(String code, Object... args) {
    this.code = code;
    this.args = args;
    this.status = HttpStatus.UNPROCESSABLE_ENTITY;
  }

}