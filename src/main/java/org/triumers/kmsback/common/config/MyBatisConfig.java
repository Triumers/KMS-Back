package org.triumers.kmsback.common.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "org.triumers.kmsback", annotationClass = Mapper.class)
public class MyBatisConfig {
}
