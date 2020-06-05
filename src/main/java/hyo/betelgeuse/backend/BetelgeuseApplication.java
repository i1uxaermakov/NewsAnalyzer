package hyo.betelgeuse.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@SpringBootApplication
@Configuration
public class BetelgeuseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetelgeuseApplication.class, args);
    }


    @Bean
    public Set<String> stopWordsList() {
        Set<String> stopSet = new HashSet<String>();

        String fileName = "/stopwords.txt";

        InputStream in = getClass().getResourceAsStream(fileName);

        Scanner scanner = new Scanner(in);
        while(scanner.hasNext()) {
            stopSet.add(scanner.nextLine().trim());
        }

        return stopSet;
    }


    @Bean
    public WordOccurrenceCounter wordOccurrenceCounter() {
        return new WordOccurrenceCounter();
    }
}
