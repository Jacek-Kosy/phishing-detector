package pl.jacekk.phishingdetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@SpringBootApplication
public class PhishingDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhishingDetectorApplication.class, args);
    }

    @Bean
    EmbeddedKafkaBroker broker() {
        return new EmbeddedKafkaBroker(1)
                .kafkaPorts(9092)
                .brokerListProperty("spring.kafka.bootstrap-servers");
    }

}
