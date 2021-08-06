package com.juniormascarenhas.assemblyvoting.factory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.juniormascarenhas.assemblyvoting.config.ApplicationConfig;
import com.juniormascarenhas.assemblyvoting.enumeration.SortOrder;
import com.juniormascarenhas.assemblyvoting.enumeration.SortingFieldEnum;
import com.juniormascarenhas.assemblyvoting.exception.MessageError;
import com.juniormascarenhas.assemblyvoting.exception.Messages;
import com.juniormascarenhas.assemblyvoting.utils.ParamConverter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QueryParamFactory {

  private final ApplicationConfig applicationConfig;

  public String createString(NativeWebRequest webRequest, String field) {
    return Optional.ofNullable(webRequest.getParameter(field)).filter(StringUtils::isNotBlank).map(String::trim)
        .orElse(null);
  }

  public Integer createLimit(NativeWebRequest webRequest, List<MessageError> errors) {
    try {
      return Optional.ofNullable(webRequest.getParameter("_limit")).filter(StringUtils::isNotBlank).map(String::trim)
          .map(ParamConverter::convertLimit).orElse(applicationConfig.getDefaultLimit());
    } catch (Exception e) {
      errors.add(new MessageError(Messages.INVALID_REQUEST_PARAM_FIELD, "_limit"));
    }
    return applicationConfig.getDefaultLimit();
  }

  public Integer createOffset(NativeWebRequest webRequest, List<MessageError> errors) {
    try {
      return Optional.ofNullable(webRequest.getParameter("_offset")).filter(StringUtils::isNotBlank).map(String::trim)
          .map(ParamConverter::convertOffset).orElse(0);
    } catch (Exception e) {
      errors.add(new MessageError(Messages.INVALID_REQUEST_PARAM_FIELD, "_offset"));
    }
    return 0;
  }

  public SortOrder createSort(NativeWebRequest webRequest, List<MessageError> errors) {
    try {
      return Optional.ofNullable(webRequest.getParameter("_sort")).filter(StringUtils::isNotBlank).map(String::trim)
          .map(String::toUpperCase).map(SortOrder::getSortByParam).orElse(SortOrder.ASC);
    } catch (Exception e) {
      errors.add(new MessageError(Messages.INVALID_REQUEST_PARAM_FIELD, "_sort"));
    }
    return SortOrder.ASC;
  }

  public SortingFieldEnum createField(NativeWebRequest webRequest, List<MessageError> errors) {
    try {
      return Optional.ofNullable(webRequest.getParameter("field")).filter(StringUtils::isNotBlank).map(String::trim)
          .map(String::toUpperCase).map(SortingFieldEnum::getSortingFieldByParam)
          .orElse(SortingFieldEnum.NAME);
    } catch (Exception e) {
      errors.add(new MessageError(Messages.INVALID_REQUEST_PARAM_FIELD, "field"));
    }
    return SortingFieldEnum.NAME;
  }

  public LocalDateTime createDate(NativeWebRequest webRequest, List<MessageError> errors, String field) {
    try {
      return Optional.ofNullable(webRequest.getParameter(field)).filter(StringUtils::isNotBlank)
          .map(ParamConverter::convertDate).orElse(null);
    } catch (Exception e) {
      errors.add(new MessageError(Messages.INVALID_REQUEST_PARAM_FIELD, field));
    }
    return null;
  }
}
