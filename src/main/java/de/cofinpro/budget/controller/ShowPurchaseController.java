package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;

import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

import static de.cofinpro.budget.controller.BudgetManager.EMPTY_PURCHASES;

/**
 * Controller Class for the show purchases sub-menu, that extends the MenuLoopController.
 */
public class ShowPurchaseController extends MenuLoopController<ShowPurchaseController.Choice> {

    private final List<Purchase> purchases;

    protected ShowPurchaseController(ConsolePrinter consolePrinter, Scanner scanner, List<Purchase> purchases) {
        super(consolePrinter, scanner);
        this.purchases = purchases;
    }

    @Override
    protected String getMenuText() {
        return """
                  
                Choose the type of purchases
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) All
                6) Back""";
    }

    @Override
    protected Runnable getMenuAction(Choice choice) {
        var categoriesToFilter = choice == Choice.ALL ?
                EnumSet.allOf(Category.class) :
                EnumSet.of(Category.valueOf(choice.name()));
        return () -> showPurchases(categoriesToFilter, choice.getText());
    }

    private void showPurchases(EnumSet<Category> categories, String text) {
        printer.printInfo("\n{}:", text);
        purchases.stream()
                .filter(p -> categories.contains(p.category()))
                .map(Purchase::toString)
                .forEach(printer::printInfo);
        var total = purchases.stream().filter(p -> categories.contains(p.category()))
                .mapToDouble(Purchase::price).sum();
        if (total != 0) {
            printer.printWithPrice("Total sum:", total);
        } else {
            printer.printInfo(EMPTY_PURCHASES);
        }
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
        FOOD, CLOTHES, ENTERTAINMENT, OTHER, ALL, BACK;

        private String getText() {
            return name().charAt(0) + name().substring(1).toLowerCase(); // capitalize
        }
    }
}
