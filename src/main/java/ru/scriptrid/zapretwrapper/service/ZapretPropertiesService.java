package ru.scriptrid.zapretwrapper.service;

/**
 * @author Ivan Selikhov
 */
public interface ZapretPropertiesService {

    void getFolderPath();

    void getBatFileName();

    String findZapretFolder(String absolutePath);

    String findBatFile(String absolutePath);

    void specifyPath();

    void specifyBat();
}
