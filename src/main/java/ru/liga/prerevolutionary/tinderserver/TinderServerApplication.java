package ru.liga.prerevolutionary.tinderserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.liga.prerevolutionary.tinderserver.image.FontRegistrar;

@SpringBootApplication
public class TinderServerApplication {

    public static void main(String[] args) {
        FontRegistrar.registerFromResource("font/OldStandardTT-Regular.ttf");
        SpringApplication.run(TinderServerApplication.class, args);
    }

}
