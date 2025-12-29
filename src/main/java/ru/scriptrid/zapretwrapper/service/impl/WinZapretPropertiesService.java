package ru.scriptrid.zapretwrapper.service.impl;

import static ru.scriptrid.zapretwrapper.common.FileUtils.parsePath;
import static ru.scriptrid.zapretwrapper.constant.WinZapretConstants.ZAPRET_FOLDER;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.scriptrid.zapretwrapper.common.ConfigService;
import ru.scriptrid.zapretwrapper.config.ZapretConfigurationProperties;
import ru.scriptrid.zapretwrapper.constant.WinZapretConstants;
import ru.scriptrid.zapretwrapper.service.ZapretPropertiesService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author Ivan Selikhov
 */
@Service
@RequiredArgsConstructor
public class WinZapretPropertiesService implements ZapretPropertiesService {


    private final Scanner scanner;
    private final ConfigService configService;
    private final ZapretConfigurationProperties zapretConfigurationProperties;

    @Override
    public void specifyPath() {
        System.out.println("""
                Specify the absolute path to your zapret-win-bundle-master.
                Note: The bol-van`s zapret-win-bundle-master is required.
                To exit from this method press Enter
                """);
        String input = scanner.nextLine();
        if (input != null) {
            String zapretPath = findZapretFolder(input);
            configService.updateZapretPath(zapretPath);
            zapretConfigurationProperties.setFolderPath(zapretPath);
        }
    }

    @Override
    public void specifyBat() {
        if (!zapretConfigurationProperties.folderPathIsSpecified()) {
            System.out.println("""
                    Zapret folder is unknown! Specify it
                    """);
            return;
        }
        System.out.println("""
                Specify name of your zapret launcher bat file
                To exit from this method press Enter
                """);
        String input = scanner.nextLine();
        if (input != null) {
            String batPath = findBatFile(input);
            if (batPath == null) {
                return;
            }
            configService.updateBatPath(batPath);
            configService.updateBatName(input);
            zapretConfigurationProperties.setBatPath(batPath);
            zapretConfigurationProperties.setBatName(input);
        }
    }

    @Override
    public String findBatFile(String input) {
        Path path = parsePath(zapretConfigurationProperties.getFolderPath());
        if (path == null) {
            System.out.println("""
                    Path to your zapret-win-bundle-master was not found.
                    Specify it by specifyPath command.
                    """);
            return null;
        }
        path = path.resolve(WinZapretConstants.BAT_FOLDER).resolve(input);
        if (path.toFile().exists()) {
            zapretConfigurationProperties.setBatName(input);
            return path.toString();
        } else {
            System.out.println("""
                        Bat not found.
                    """);
            return null;
        }

    }

    @Override
    public String findZapretFolder(String absolutePath) {
        absolutePath = absolutePath.trim().replaceAll("\"", "");
        Path path = parsePath(absolutePath);
        if (path == null) return absolutePath;

        if (Files.isDirectory(path)) {
            try (Stream<Path> pathStream = Files.walk(path, 1)) {
                Optional<Path> found = pathStream.filter(current -> current.getFileName().toString().equals(ZAPRET_FOLDER))
                        .filter(Files::isDirectory)
                        .findFirst();
                if (found.isPresent()) {
                    path = path.resolve(found.get());
                    System.out.println("Found zapret-bundle.");
                    zapretConfigurationProperties.setFolderPath(path.toAbsolutePath().toString());
                    return zapretConfigurationProperties.getFolderPath();
                }
            } catch (IOException e) {
                System.out.println("Could not find zapret-bundle.");
            }
        } else {
            System.out.println("Could not find zapret-bundle.");
        }
        return absolutePath;
    }
}

