package ru.scriptrid.zapretwrapper.console;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.scriptrid.zapretwrapper.menu.MainMenu;

@Component
@RequiredArgsConstructor
class CommandLine implements CommandLineRunner {

    private final MainMenu mainMenu;

    @Override
    public void run(String... args) {
        mainMenu.menu();
    }
}