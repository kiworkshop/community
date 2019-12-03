package community.common.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

public class CommonBeanCreators {
  private CommonBeanCreators() {}

  public static ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
        .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
        .setFieldMatchingEnabled(true);
    return modelMapper;
  }
}
