package com.learn.spring.web.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 17:12
 */
@Configuration
@PropertySource("classpath:config.properties")
@Data
public class WebConfigurations {

}
