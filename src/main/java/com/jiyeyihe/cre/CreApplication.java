package com.jiyeyihe.cre;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



@EnableEurekaClient
@EnableApolloConfig
@SpringBootApplication
@MapperScan("com.jiyeyihe.cre.webapp.mapper")
public class CreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreApplication.class, args);

    }
}
