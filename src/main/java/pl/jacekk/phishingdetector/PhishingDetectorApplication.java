package pl.jacekk.phishingdetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PhishingDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhishingDetectorApplication.class, args);
    }
}
