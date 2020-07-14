package org.kiworkshop.community.content.config;

import org.kiworkshop.community.common.config.CommonBeanCreators;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
  @Bean
  public ModelMapper modelMapper() {
    return CommonBeanCreators.modelMapper();
  }
}
