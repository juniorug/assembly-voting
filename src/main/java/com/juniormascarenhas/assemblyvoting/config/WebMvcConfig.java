package com.juniormascarenhas.assemblyvoting.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.juniormascarenhas.assemblyvoting.component.QueryParamResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final ObjectMapper objectMapper;

  private final QueryParamResolver queryParamResolver;

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(ConfigConstants.FIELD_FILTER_NAME,
        SimpleBeanPropertyFilter.serializeAll()));
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
    converters.add(converter);
    WebMvcConfigurer.super.extendMessageConverters(converters);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(queryParamResolver);
    // resolvers.add(otherResolver);
  }
}