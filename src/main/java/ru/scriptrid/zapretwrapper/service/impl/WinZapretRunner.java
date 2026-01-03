package ru.scriptrid.zapretwrapper.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.scriptrid.zapretwrapper.config.ZapretConfigurationProperties;
import ru.scriptrid.zapretwrapper.service.ZapretRunner;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * @author Ivan Selikhov
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WinZapretRunner implements ZapretRunner {

    private static final String WINWS_PROCESS = "winws.exe";
    private final ZapretConfigurationProperties zapretConfigurationProperties;
    private final ExecutorService executorService;

    private long zapretPid;

    @Override
    public void lanchZapret() {
        if (!zapretConfigurationProperties.batPathIsSpecified()) {
            System.out.println("You need to specify bat/cmd of zapret launcher path");
            return;
        }
        String execPath = zapretConfigurationProperties.getBatPath();

        File execFile = new File(execPath);
        execFile.setExecutable(true);
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", execPath);

        executorService.submit(() -> {
            try {
                Process zapretProcess = processBuilder.start();
                this.zapretPid = specifyPidOfWinws(); //TODO not working
                System.out.println("Started Zapret Process. PID " + zapretPid);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void killZapret() {
        Optional<ProcessHandle> processHandle = ProcessHandle.of(this.zapretPid);
        processHandle.ifPresent(ProcessHandle::destroy);
    }

    private long specifyPidOfWinws() {
        return ProcessHandle.allProcesses()
                .filter(ph -> ph.info().command()
                        .map(cmd -> cmd.toLowerCase().endsWith(WINWS_PROCESS.toLowerCase()))
                        .orElse(false))
                .map(java.lang.ProcessHandle::pid)
                .findFirst().orElse(0L);
    }
}
