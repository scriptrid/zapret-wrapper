package ru.scriptrid.zapretwrapper.menu;

import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Ivan Selikhov
 */
public abstract class Menu {
    protected static final String EXIT_CODE = "exit!";
    protected static final String BACK = "back";
    protected static final Scanner SCANNER = new Scanner(System.in);

    protected Map<String, Runnable> functional;
    protected final String menuName = provideMenuName();

    @PostConstruct
    public void init() {
        functional = functions();
    }

    public final void menu() {
        while (true) {
            printOptions();
            String selectedOption = SCANNER.nextLine();
            if (selectedOption.equals(EXIT_CODE)) {
                shutdown();
            }
            if (selectedOption.equals(BACK)) {
                return;
            }
            Set<String> options = functional.keySet();
            if (options.contains(selectedOption)) {
                functional.get(selectedOption).run();
            } else {
                System.out.printf("""
                        Invalid option.
                            To return type %s
                            To close app type %s
                        """, BACK, EXIT_CODE);
            }
        }
    }

    protected void printOptions() {
        System.out.printf("%s options:\n", menuName);
        functional.keySet().stream().forEach(optionName -> System.out.printf(" - %s\n", optionName));
    }

    private void shutdown() {
        System.exit(0);
    }

    protected abstract Map<String, Runnable> functions();

    protected abstract String provideMenuName();
}
