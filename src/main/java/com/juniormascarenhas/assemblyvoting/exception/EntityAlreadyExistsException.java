package com.juniormascarenhas.assemblyvoting.exception;

public class EntityAlreadyExistsException extends UnprocessableEntityException {

  private static final long serialVersionUID = -5359628564531578236L;

  public EntityAlreadyExistsException(String attribute) {
    super(Messages.ENTITY_ALREADY_EXISTS, attribute);
  }

}
