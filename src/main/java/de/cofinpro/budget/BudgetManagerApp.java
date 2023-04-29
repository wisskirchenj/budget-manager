package de.cofinpro.budget;

import de.cofinpro.budget.controller.BudgetManager;
import de.cofinpro.budget.io.ConsolePrinter;

import java.util.Locale;
import java.util.Scanner;

public class BudgetManagerApp {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // in Germany, double is formatted with a comma
        new BudgetManager(new ConsolePrinter(), new Scanner(System.in)).run();
    }
}
