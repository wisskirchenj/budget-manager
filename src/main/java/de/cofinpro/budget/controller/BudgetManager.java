package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;

import java.util.Scanner;

public class BudgetManager {

    private final ConsolePrinter printer;
    private final Scanner scanner;

    public BudgetManager(ConsolePrinter consolePrinter, Scanner scanner) {
        this.printer = consolePrinter;
        this.scanner = scanner;
    }

    public void run() {
        printer.printWithPrice("\nTotal:",
                scanner.useDelimiter("\n").tokens()
                        .mapToDouble(line -> {
                            printer.printInfo(line);
                            return Double.parseDouble(line.substring(line.lastIndexOf("$") + 1));
                        }).sum());
    }
}
