package de.cofinpro.budget.model;

public record Purchase(String item, double price) {

    @Override
    public String toString() {
        return "%s $%.2f".formatted(item, price);
    }
}
