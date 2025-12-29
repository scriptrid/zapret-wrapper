package ru.scriptrid.zapretwrapper.service;

/**
 * @author Ivan Selikhov
 */
public interface ZapretService {

    void getPath();

    String findZapret(String absolutePath);

    void specifyPath();
}
