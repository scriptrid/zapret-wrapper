package ru.scriptrid.zapretwrapper.common;

import lombok.experimental.UtilityClass;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Ivan Selikhov
 */
@UtilityClass
public class FileUtils {

    public static Path parsePath(String absolutePath) {
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
