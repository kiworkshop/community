package org.kiworkshop.community.content.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setMethodAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setFieldMatchingEnabled(true);
    return modelMapper;
  }
}
