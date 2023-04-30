package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static de.cofinpro.budget.controller.BudgetManager.EMPTY_PURCHASES;

public class PurchaseDisplayer {

    private final List<Purchase> purchases;
    private final ConsolePrinter printer;

    public PurchaseDisplayer(List<Purchase> purchases, ConsolePrinter printer) {
        this.purchases = purchases;
        this.printer = printer;
    }

    public void showPurchases(Set<Category> categories, String text) {
        printer.printInfo("\n{}:", text);
        purchases.stream()
                .filter(p -> categories.contains(p.category()))
                .sorted(Comparator.comparingDouble(Purchase::price).reversed())
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
}
