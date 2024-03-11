package it.uniroma1.speeddetectoranalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"it.uniroma1.commons","it.uniroma1.speeddetectoranalyzer"})
public class SpeedDetectorAnalyzerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpeedDetectorAnalyzerApplication.class, args);

    }
}
