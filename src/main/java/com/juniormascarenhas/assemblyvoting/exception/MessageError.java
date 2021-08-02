package com.juniormascarenhas.assemblyvoting.exception;

import java.io.Serializable;

import com.juniormascarenhas.assemblyvoting.component.Dictionary;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class MessageError implements Serializable {

  private static final long serialVersionUID = 5256651766510189965L;

  private String code;
  private String field;
  private String message;

  public MessageError(String code, String field) {
    this.code = code;
    this.field = field;
    this.message = Dictionary.valueOf(code, field);
  }

  public MessageError(String code, Object... args) {
    this.code = code;
    this.field = args.length > 0 ? args[0].toString() : null;
    this.message = Dictionary.valueOf(code, args);
  }

}
