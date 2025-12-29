package ru.scriptrid.zapretwrapper.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Ivan Selikhov
 */
@Service
@RequiredArgsConstructor
public class MainMenu extends Menu {

    private final ZapretMenu zapretMenu;

    @Override
    protected Map<String, Runnable> functions() {
        return Map.of("zapret", zapretMenu::menu);
    }

    @Override
    protected String provideMenuName() {
        return "Main menu";
    }
}
