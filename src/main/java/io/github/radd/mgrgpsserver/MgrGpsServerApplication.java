package io.github.radd.mgrgpsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MgrGpsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MgrGpsServerApplication.class, args);
    }

}
