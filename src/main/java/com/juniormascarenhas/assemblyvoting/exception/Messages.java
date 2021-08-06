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
  public static final String SIZE_137_INVALID_LENGTH_ERROR = "412.013";
  public static final String SIZE_11_INVALID_LENGTH_ERROR = "412.014";
  /* Open session related error codes */
  public static final String STATUS_RESQUESTED_MUST_BE_OPENED = "412.015";
  /* Vote related error codes */
  public static final String SESSION_NOT_OPENED = "412.016";
  public static final String ASSOCIATED_UNABLE_TO_VOTE = "412.017";
  public static final String ASSOCIATED_ALREADY_VOTED = "412.018";

  public static final String ENTITY_ALREADY_EXISTS = "422.001";
  public static final String ENTITY_DOES_NOT_EXISTS = "422.002";
  /* Open session related error codes */
  public static final String SESSION_ALREADY_OPENED = "422.003";
  public static final String SESSION_ALREADY_CLOSED = "422.004";

}
