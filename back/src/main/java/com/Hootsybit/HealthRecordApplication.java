package com.Hootsybit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@SpringBootApplication
public class HealthRecordApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(HealthRecordApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}