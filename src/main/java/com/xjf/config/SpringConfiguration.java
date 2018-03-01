package com.xjf.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Import;

@Configurable
@MapperScan("com.xjf.mapper")
@Import(SpringWebConfiguration.class)
public class SpringConfiguration {
}
