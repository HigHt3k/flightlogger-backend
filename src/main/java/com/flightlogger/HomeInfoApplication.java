package com.flightlogger;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySources;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEncryptableProperties
@EncryptablePropertySources({@EncryptablePropertySource("application.properties")})
public class HomeInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeInfoApplication.class, args);
    }

}
