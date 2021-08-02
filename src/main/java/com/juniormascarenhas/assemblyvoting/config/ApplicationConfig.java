package com.juniormascarenhas.assemblyvoting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class ApplicationConfig {

  @Value("${search.configuration.accept-range}")
  private Integer acceptRange;

  @Value("${search.configuration.default-limit}")
  private Integer defaultLimit;
}
