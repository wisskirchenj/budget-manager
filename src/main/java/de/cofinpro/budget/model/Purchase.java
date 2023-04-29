package de.cofinpro.budget.model;

/**
 * the immutable purchase model record
 */
public record Purchase(String item, Category category, double price) {

    /**
     * convenience constructor for backwards compatibility with former stages, that knew no categories.
     */
    public Purchase(String item, double price) {
        this(item, Category.OTHER, price);
    }

    @Override
    public String toString() {
        return "%s $%.2f".formatted(item, price);
    }
}
