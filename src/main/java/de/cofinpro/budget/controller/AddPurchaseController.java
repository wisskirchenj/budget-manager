package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;

import java.util.List;
import java.util.Scanner;

/**
 * Controller Class for the add purchases sub-menu, that extends the MenuLoopController.
 */
public class AddPurchaseController extends MenuLoopController<AddPurchaseController.Choice> {

    private final List<Purchase> purchases;

    protected AddPurchaseController(ConsolePrinter consolePrinter, Scanner scanner, List<Purchase> purchases) {
        super(consolePrinter, scanner);
        this.purchases = purchases;
    }

    @Override
    protected String getMenuText() {
        return """
                                  
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) Back""";
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
        purchases.add(new Purchase(item, category, price));
        printer.printInfo("Purchase was added!");
    }

    @Override
    protected Choice getExitChoice() {
        return Choice.BACK;
    }

    @Override
    protected int menuStartIndex() {
        return 1;
    }

    protected enum Choice {
        FOOD, CLOTHES, ENTERTAINMENT, OTHER, BACK
    }
}
