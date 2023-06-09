package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.BudgetState;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;

import java.util.Scanner;

/**
 * Controller Class for the add purchases sub-menu, that extends the MenuLoopController.
 */
public class AddPurchaseController extends MenuLoopController<AddPurchaseController.Choice> {

    private final BudgetState budgetState;

    protected AddPurchaseController(ConsolePrinter consolePrinter, Scanner scanner, BudgetState budgetState) {
        super(consolePrinter, scanner);
        this.budgetState = budgetState;
    }

    @Override
    protected String getMenuText() {
        return "\nChoose the type of purchase\n" + Category.SELECTION +"\n5) Back";
    }

    @Override
    protected Runnable getMenuAction(Choice choice) {
        return () -> addPurchase(Category.valueOf(choice.name()));
    }

    private void addPurchase(Category category) {
        printer.printInfo("\nEnter purchase name:");
        var item = scanner.nextLine();
        printer.printInfo("Enter its price:");
        var price = Double.parseDouble(scanner.nextLine());
        budgetState.getPurchases().add(new Purchase(item, category, price));
        budgetState.setBalance(Math.max(0, budgetState.getBalance() - price));
        printer.printInfo("Purchase was added!");
    }

    @Override
    protected Choice getExitChoice() {
        return Choice.BACK;
    }

    protected enum Choice {
        FOOD, CLOTHES, ENTERTAINMENT, OTHER, BACK
    }
}
