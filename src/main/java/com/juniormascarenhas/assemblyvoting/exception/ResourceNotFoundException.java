package com.juniormascarenhas.assemblyvoting.exception;

public class ResourceNotFoundException extends NotFoundException {

  private static final long serialVersionUID = -6482607805043595507L;

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
