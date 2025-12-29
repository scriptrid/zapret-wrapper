package ru.scriptrid.zapretwrapper.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.scriptrid.zapretwrapper.service.ZapretService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Selikhov
 */
@Component
@RequiredArgsConstructor
public class ZapretMenu extends  Menu {

    private final ZapretService zapretService;

    @Override
    protected Map<String, Runnable> functions() {
        Map<String, Runnable> functions = new HashMap<>();
        functions.put("path", () -> zapretService.getPath());
        functions.put("specifyPath", () -> zapretService.specifyPath());
        return functions;
    }

    @Override
    protected String provideMenuName() {
        return "zapret menu";
    }
}
