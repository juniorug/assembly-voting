package com.juniormascarenhas.assemblyvoting.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

  public static final String INVALID_BODY_ERROR = "412.000";
  public static final String FIELD_REQUIRED_ERROR = "412.001";
  public static final String MISSING_HEADER_ERROR = "412.002";
  public static final String INVALID_LENGTH_ERROR = "412.003";
  public static final String INVALID_LENGTH_FOR_VALIDATION_TYPE_ERROR = "412.004";
  public static final String SIZE_1024_INVALID_LENGTH_ERROR = "412.005";
  public static final String FIELD_INVALID_VALUE = "412.006";
  public static final String UUID_INVALID_LENGTH_ERROR = "412.007";
  public static final String UUID_INVALID_FORMAT_ERROR = "412.008";
  public static final String INVALID_DATE_PERIOD = "412.009";
  public static final String INVALID_REQUEST_PARAM_FIELD = "412.010";
  public static final String INVALID_FORMAT_ERROR = "412.011";
  public static final String FIELD_MUST_NOT_BE_EMPTY_ERROR = "412.012";

  public static final String ENTITY_ALREADY_EXISTS = "422.001";
  public static final String ENTITY_DOES_NOT_EXISTS = "422.002";
  public static final String SESSION_ALREADY_CLOSED = "422.003";
}
