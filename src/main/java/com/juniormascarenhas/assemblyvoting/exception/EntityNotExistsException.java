package com.juniormascarenhas.assemblyvoting.exception;

public class EntityNotExistsException extends UnprocessableEntityException {

  private static final long serialVersionUID = -1846468483595331777L;

  public EntityNotExistsException(String attribute) {
    super(Messages.ENTITY_DOES_NOT_EXISTS, attribute);
  }

}
