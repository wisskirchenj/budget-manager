package de.cofinpro.budget.model;

public enum Category {
    FOOD,
    CLOTHES,
    ENTERTAINMENT,
    OTHER;

    public String capitalized() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public static final String SELECTION = """
            1) Food
            2) Clothes
            3) Entertainment
            4) Other""";
}
