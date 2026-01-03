package ru.scriptrid.zapretwrapper.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.scriptrid.zapretwrapper.config.ZapretConfigurationProperties;
import ru.scriptrid.zapretwrapper.service.ZapretPropertiesService;
import ru.scriptrid.zapretwrapper.service.ZapretRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Selikhov
 */
@Component
@RequiredArgsConstructor
public class ZapretMenu extends  Menu {

    private final ZapretPropertiesService propertiesService;
    private final ZapretRunner runner;
    private final ZapretConfigurationProperties zapretConfigurationProperties;

    @Override
    protected Map<String, Runnable> functions() {
        Map<String, Runnable> functions = new HashMap<>();
        functions.put("properties", () -> System.out.println(this.zapretConfigurationProperties));
        functions.put("specifyPath", propertiesService::specifyPath);
        functions.put("specifyBat", propertiesService::specifyBat);
        functions.put("launch", runner::lanchZapret);
        functions.put("kill", runner::killZapret);
        return functions;
    }

    @Override
    protected String provideMenuName() {
        return "zapret menu";
    }


}
