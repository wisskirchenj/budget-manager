package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

public class AnalyzeController extends MenuLoopController<AnalyzeController.Choice> {

    private final List<Purchase> purchases;

    public AnalyzeController(ConsolePrinter printer, Scanner scanner, List<Purchase> purchases) {
        super(printer, scanner);
        this.purchases = purchases;
    }

    @Override
    protected String getMenuText() {
        return """
                
                How do you want to sort?
                1) Sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back""";
    }

    @Override
    protected Runnable getMenuAction(Choice choice) {
        return Map.<Choice, Runnable>of(
                Choice.SORT_ALL, this::sortAll,
                Choice.SORT_BY_CAT, this::sortByCategory,
                Choice.SORT_CAT, this::sortWithinCategory
        ).get(choice);
    }

    private void sortWithinCategory() {
        printer.printInfo("\nChoose the type of purchase\n" + Category.SELECTION);
        var category = Category.values()[Integer.parseInt(scanner.nextLine()) - 1];
        new PurchaseDisplayer(purchases, printer).showPurchases(EnumSet.of(category), category.capitalized());
    }

    private void sortByCategory() {
        printer.printInfo("\nTypes:");
        purchases.stream()
                .collect(groupingBy(Purchase::category, reducing(0d, Purchase::price, Double::sum)))
                .entrySet().stream().sorted(Map.Entry.<Category, Double>comparingByValue().reversed())
                .map(entry -> "%s - $%.2f".formatted(entry.getKey().capitalized(), entry.getValue()))
                .forEach(printer::printInfo);
        printer.printWithPrice("Total sum:", purchases.stream().mapToDouble(Purchase::price).sum());
    }

    private void sortAll() {
        new PurchaseDisplayer(purchases, printer).showPurchases(EnumSet.allOf(Category.class), "All");
    }

    @Override
    protected Choice getExitChoice() {
        return Choice.BACK;
    }

    protected enum Choice {
        SORT_ALL, SORT_BY_CAT, SORT_CAT, BACK
    }
}
