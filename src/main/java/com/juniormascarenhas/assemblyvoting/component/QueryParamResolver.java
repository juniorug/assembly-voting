package com.juniormascarenhas.assemblyvoting.component;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.juniormascarenhas.assemblyvoting.factory.GetQueryParamFactory;
import com.juniormascarenhas.assemblyvoting.request.GetQueryParam;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QueryParamResolver implements HandlerMethodArgumentResolver {

  private final GetQueryParamFactory factory;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(GetQueryParam.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    return factory.createGetQueryParam(webRequest);
  }
}
//