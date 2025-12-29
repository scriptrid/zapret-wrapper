package ru.scriptrid.zapretwrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
                findZapret(input);
            }
        }
        System.out.println(absolutePath);
    }

    @Override
    public void findZapret(String absolutePath) {
        absolutePath = absolutePath.trim().replaceAll("\"", "");
        Path path = returnPath(absolutePath);
        if (path == null) return;

        if (Files.isDirectory(path)) {
            try (Stream<Path> pathStream = Files.walk(path, 1)) {
                Optional<Path> found = pathStream.filter(current -> current.getFileName().toString().equals(ZAPRET_FOLDER))
                        .filter(Files::isDirectory)
                        .findFirst();
                if (found.isPresent()) {
                    path = path.resolve(found.get());
                    System.out.println("Found zapret-bundle.");
                    this.absolutePath = path.toAbsolutePath().toString();
                }
            } catch (IOException e) {
                System.out.println("Could not find zapret-bundle.");
            }
        } else {
            System.out.println("Could not find zapret-bundle.");
        }
    }

    private Path returnPath(String absolutePath) {
        Path path;
        try {
            path = Paths.get(absolutePath);
        } catch (InvalidPathException e) {
            System.out.println("Invalid path: " + absolutePath);
            return null;
        }
        return path;
    }
}

