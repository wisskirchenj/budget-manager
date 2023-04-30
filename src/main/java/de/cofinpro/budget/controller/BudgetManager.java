package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.io.StateSerializer;
import de.cofinpro.budget.model.BudgetState;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Controller Class for the budget manager app, that extends the MenuLoopController for the main menu functionality.
 */
public class BudgetManager extends MenuLoopController<BudgetManager.Choice> {

    static final String EMPTY_PURCHASES = "The purchase list is empty";
    private BudgetState budgetState = new BudgetState(new ArrayList<>());

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
        budgetState.setBalance(budgetState.getBalance() + Double.parseDouble(scanner.nextLine()));
        printer.printInfo("Income was added!");
    }

    private void addPurchase() {
        new AddPurchaseController(printer, scanner, budgetState).run();
    }

    private void showPurchases() {
        if (budgetState.getPurchases().isEmpty()) {
            printer.printInfo("\n{}", EMPTY_PURCHASES);
            return;
        }
        new ShowPurchaseController(printer, scanner, budgetState.getPurchases()).run();
    }

    private void showBalance() {
        printer.printWithPrice("\nBalance:", budgetState.getBalance());
    }

    private void saveState() {
        new StateSerializer(printer).serialize(budgetState);
        printer.printInfo("\nPurchases were saved!");
    }

    private void loadState() {
        budgetState = new StateSerializer(printer).deserialize();
        printer.printInfo("\nPurchases were loaded!");
    }

    private void analyze() {
        new AnalyzeController(printer, scanner, budgetState.getPurchases()).run();
    }

    @Override
    protected String getMenuText() {
        return """
                                                                
                Choose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                5) Save
                6) Load
                7) Analyze (Sort)
                0) Exit""";
    }

    @Override
    protected Runnable getMenuAction(Choice choice) {
        return Map.<Choice, Runnable>of(
                Choice.ADD_INCOME, this::addIncome,
                Choice.ADD_PURCHASE, this::addPurchase,
                Choice.SHOW_PURCHASES, this::showPurchases,
                Choice.BALANCE, this::showBalance,
                Choice.SAVE, this::saveState,
                Choice.LOAD, this::loadState,
                Choice.ANALYZE, this::analyze
        ).get(choice);
    }

    @Override
    protected Choice getExitChoice() {
        return Choice.EXIT;
    }

    @Override
    protected int menuStartIndex() {
        return 0;
    }

    protected enum Choice {
        EXIT, ADD_INCOME, ADD_PURCHASE, SHOW_PURCHASES, BALANCE, SAVE, LOAD, ANALYZE
    }
}
