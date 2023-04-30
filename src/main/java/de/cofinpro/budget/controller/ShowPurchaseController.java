package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;

import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

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
        return "\nChoose the type of purchases\n" + Category.SELECTION +"\n5) All\n6) Back";
    }

    @Override
    protected Runnable getMenuAction(Choice choice) {
        var categoriesToFilter = choice == Choice.ALL ?
                EnumSet.allOf(Category.class) :
                EnumSet.of(Category.valueOf(choice.name()));
        return () -> new PurchaseDisplayer(purchases, printer)
                .showPurchases(categoriesToFilter, choice.getText());
    }

    @Override
    protected Choice getExitChoice() {
        return Choice.BACK;
    }

    protected enum Choice {
        FOOD, CLOTHES, ENTERTAINMENT, OTHER, ALL, BACK;

        private String getText() {
            return name().charAt(0) + name().substring(1).toLowerCase(); // capitalize
        }
    }
}
