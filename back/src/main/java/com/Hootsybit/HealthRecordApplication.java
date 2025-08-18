package com.Hootsybit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthRecordApplication implements CommandLineRunner {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthRecordApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(HealthRecordApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}