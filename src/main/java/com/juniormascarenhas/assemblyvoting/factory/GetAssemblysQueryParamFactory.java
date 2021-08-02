package com.juniormascarenhas.assemblyvoting.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.juniormascarenhas.assemblyvoting.exception.InvalidParamException;
import com.juniormascarenhas.assemblyvoting.exception.MessageError;
import com.juniormascarenhas.assemblyvoting.request.GetAssemblysQueryParam;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetAssemblysQueryParamFactory {

  private final QueryParamFactory queryParamFactory;

  public GetAssemblysQueryParam createGetAssemblysQueryParam(NativeWebRequest webRequest) {
    List<MessageError> errors = new ArrayList<>();

    GetAssemblysQueryParam.GetAssemblysQueryParamBuilder builder = GetAssemblysQueryParam.builder()
        .keywords(queryParamFactory.createString(webRequest, "keywords"))
        .limit(queryParamFactory.createLimit(webRequest, errors))
        .offset(queryParamFactory.createOffset(webRequest, errors))
        .sort(queryParamFactory.createSort(webRequest, errors));

    if (!errors.isEmpty()) {
      throw new InvalidParamException(errors);
    }

    return builder.build();
  }
}
