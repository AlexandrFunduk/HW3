package ru.liga.prerevolutionary.tinderserver.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionary.tinderserver.image.FontRegistrar;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        FontRegistrar.registerFromResource("font/OldStandardTT-Regular.ttf");
    }
}
