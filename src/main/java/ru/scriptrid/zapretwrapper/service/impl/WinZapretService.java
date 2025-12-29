package ru.scriptrid.zapretwrapper.service.impl;

import static ru.scriptrid.zapretwrapper.common.FileUtils.parsePath;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.scriptrid.zapretwrapper.common.ConfigService;
import ru.scriptrid.zapretwrapper.service.ZapretService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author Ivan Selikhov
 */
@Service
@RequiredArgsConstructor
public class WinZapretService implements ZapretService {

    private static final String ZAPRET_FOLDER = "zapret-win-bundle-master";
    private final Scanner scanner;
    private final ConfigService configService;
    @Value("${zapret.absolute-path:null}")
    private String absolutePath;

    @Override
    public void getPath() {
        if (absolutePath.equals("null")) {
            System.out.println("""
                    You must to specify absolute path to your zapret-win-bundle-master.
                    You may add it to application.yaml config.
                    Or type it right now.
                    Note: The bol-van`s zapret-win-bundle-master is required.
                    To exit from this method press Enter
                    """);
            String input = scanner.nextLine();
            if (input == null) {
                return;
            } else {
                String absolutePath = findZapret(input);
                configService.updateZapretPath(this.absolutePath);
            }
        }
        System.out.println(absolutePath);
    }

    @Override
    public void specifyPath() {
        System.out.println("""
                    Specify the absolute path to your zapret-win-bundle-master.
                    You may add it to application.yaml config.
                    Or type it right now.
                    Note: The bol-van`s zapret-win-bundle-master is required.
                    To exit from this method press Enter
                    """);
        String input = scanner.nextLine();
        if (input == null) {
            return;
        } else {
            findZapret(input);
        }
    }

    @Override
    public String findZapret(String absolutePath) {
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
                    this.absolutePath = path.toAbsolutePath().toString();
                    return this.absolutePath;
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

