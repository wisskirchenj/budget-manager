package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Controller Class for the budget manager app, that extends the MenuLoopController for the main menu functionality.
 */
public class BudgetManager extends MenuLoopController<BudgetManager.Choice> {


    static final String EMPTY_PURCHASES = "The purchase list is empty";
    private final List<Purchase> purchases = new ArrayList<>();
    private double incomeAdded;

    public BudgetManager(ConsolePrinter consolePrinter, Scanner scanner) {
        super(consolePrinter, scanner);
    }

    /**
     * entry point method doing the menu loop and triggering the actions chosen.
     */
    @Override
    public void run() {
        super.run();
        printer.printInfo("\nBye!");
    }

    private void addIncome() {
        printer.printInfo("\nEnter income:");
        incomeAdded += Double.parseDouble(scanner.nextLine());
        printer.printInfo("Income was added!");
    }

    private void addPurchase() {
        new AddPurchaseController(printer, scanner, purchases).run();
    }

    private void showPurchases() {
        if (purchases.isEmpty()) {
            printer.printInfo("\n{}", EMPTY_PURCHASES);
            return;
        }
        new ShowPurchaseController(printer, scanner, purchases).run();
    }

    private double totalSpent() {
        return purchases.stream().mapToDouble(Purchase::price).sum();
    }

    private void showBalance() {
        printer.printWithPrice("\nBalance:", Math.max(0, incomeAdded - totalSpent()));
    }

    @Override
    protected String getMenuText() {
        return """
                                                                
                Choose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                0) Exit""";
    }

    @Override
    protected Runnable getMenuAction(Choice choice) {
        return Map.<Choice, Runnable>of(
                Choice.ADD_INCOME, this::addIncome,
                Choice.ADD_PURCHASE, this::addPurchase,
                Choice.SHOW_PURCHASES, this::showPurchases,
                Choice.BALANCE, this::showBalance
        ).get(choice);
    }

    @Override
    protected Choice getExitChoice() {
        return Choice.EXIT;
    }

    protected enum Choice {
        EXIT, ADD_INCOME, ADD_PURCHASE, SHOW_PURCHASES, BALANCE
    }
}
