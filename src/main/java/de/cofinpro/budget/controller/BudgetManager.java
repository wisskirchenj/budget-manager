package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Controller Class for the budget manager app, that does the actual menu loop and implements its action.
 * At present, for the small functionality, it keeps all the application state, i.e. the purchases and balance.
 */
public class BudgetManager {

    private static final String MENU_TEXT  = """
                                                
                                                Choose your action:
                                                1) Add income
                                                2) Add purchase
                                                3) Show list of purchases
                                                4) Balance
                                                0) Exit""";

    private final Map<Choice, Runnable> menuActions = Map.of(
            Choice.ADD_INCOME, this::addIncome,
            Choice.ADD_PURCHASE, this::addPurchase,
            Choice.SHOW_PURCHASES, this::showPurchases,
            Choice.BALANCE, this::showBalance
    );
    private final ConsolePrinter printer;
    private final Scanner scanner;
    private final List<Purchase> purchases = new ArrayList<>();
    private double balance = 0;

    public BudgetManager(ConsolePrinter consolePrinter, Scanner scanner) {
        this.printer = consolePrinter;
        this.scanner = scanner;
    }

    /**
     * entry point method doing the menu loop and triggering the actions chosen.
     */
    public void run() {
        var choice = getMenuChoice();
        while (choice != Choice.EXIT) {
            menuActions.get(choice).run();
            choice = getMenuChoice();
        }
        printer.printInfo("\nBye!");
    }

    private void addIncome() {
        printer.printInfo("\nEnter income:");
        balance += Double.parseDouble(scanner.nextLine());
        printer.printInfo("Income was added!");
    }

    private void addPurchase() {
        printer.printInfo("\nEnter purchase name:");
        var item = scanner.nextLine();
        printer.printInfo("Enter its price:");
        var price = Double.parseDouble(scanner.nextLine());
        balance = Math.max(0, balance - price);
        purchases.add(new Purchase(item, price));
        printer.printInfo("Purchase was added!");
    }

    private void showPurchases() {
        printer.printInfo("");
        if (purchases.isEmpty()) {
            printer.printInfo("The purchase list is empty");
            return;
        }
        purchases.stream().map(Purchase::toString).forEach(printer::printInfo);
        printer.printWithPrice("Total sum:", purchases.stream().mapToDouble(Purchase::price).sum());
    }

    private void showBalance() {
        printer.printWithPrice("\nBalance:", balance);
    }

    private Choice getMenuChoice() {
        printer.printInfo(MENU_TEXT);
        return Choice.values()[Integer.parseInt(scanner.nextLine())];
    }

    private enum Choice {
        EXIT, ADD_INCOME, ADD_PURCHASE, SHOW_PURCHASES, BALANCE
    }
}
