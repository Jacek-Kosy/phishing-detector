package pl.jacekk.phishingdetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@EnableFeignClients
@SpringBootApplication
public class PhishingDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhishingDetectorApplication.class, args);
    }
}
