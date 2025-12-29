package ru.scriptrid.zapretwrapper.common;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ivan Selikhov
 */
@Component
public class ConfigService {
    private final String configPath = "src/main/resources/application.yaml";

    public void updateZapretPath(String absolutePath) {
        try {
            updateYaml("zapret.absolute-path", absolutePath);
        } catch (IOException e) {
            System.out.println("Error while updating zapret.absolute-path");
        }
    }

    private void updateYaml(String key, String value) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> config;

        File configFile = new File(configPath);

        if (configFile.exists()) {
            config = yaml.load(new FileReader(configFile));
        } else {
            config = new LinkedHashMap<>();
        }

        updateNestedMap(config, key, value);

        // Сохраняем обратно
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        yaml = new Yaml(options);
        yaml.dump(config, new FileWriter(configFile));
    }

    private void updateNestedMap(Map<String, Object> map, String key, Object value) {
        String[] keys = key.split("\\.");
        for (int i = 0; i < keys.length - 1; i++) {
            map = (Map<String, Object>) map.computeIfAbsent(
                    keys[i], k -> new LinkedHashMap<>()
            );
        }
        map.put(keys[keys.length - 1], value);
    }
}
