package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

import static de.cofinpro.budget.controller.BudgetManager.EMPTY_PURCHASES;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@MockitoSettings
class PurchaseDisplayerTest {

    static {
        Locale.setDefault(Locale.US);
    }

    @Mock
    ConsolePrinter printerMock;
    PurchaseDisplayer purchaseDisplayer;
    List<Purchase> purchases = List.of(
            new Purchase("Gum", Category.FOOD, 0.79),
            new Purchase("TV", Category.ENTERTAINMENT, 1459.99),
            new Purchase("Bananas", Category.FOOD, 2.79),
            new Purchase("Shoes", Category.CLOTHES, 144.49)
    );

    @BeforeEach
    void setUp() {
        purchaseDisplayer = new PurchaseDisplayer(purchases, printerMock);
    }

    @Test
    void whenEmptyCategory_emptyMessageDisplayed() {
        purchaseDisplayer.showPurchases(EnumSet.of(Category.OTHER), "Other");
        verify(printerMock).printInfo(EMPTY_PURCHASES);
    }

    @Test
    void whenShowCalled_purchasesFilteredSortedAndAggregated() {
        purchaseDisplayer.showPurchases(EnumSet.allOf(Category.class), "All");
        InOrder inOrder = inOrder(printerMock);
        inOrder.verify(printerMock).printInfo("\n{}:","All");
        inOrder.verify(printerMock).printInfo("TV $1459.99");
        inOrder.verify(printerMock).printInfo("Shoes $144.49");
        inOrder.verify(printerMock).printInfo("Bananas $2.79");
        inOrder.verify(printerMock).printInfo("Gum $0.79");
        inOrder.verify(printerMock).printWithPrice("Total sum:", 1608.06);
    }

    @Test
    void whenShowCalledForAll_purchasesSortedAndAggregated() {
        purchaseDisplayer.showPurchases(EnumSet.of(Category.FOOD), "Food");
        InOrder inOrder = inOrder(printerMock);
        inOrder.verify(printerMock).printInfo("Bananas $2.79");
        inOrder.verify(printerMock).printInfo("Gum $0.79");
        inOrder.verify(printerMock).printWithPrice("Total sum:", 3.58);
    }
}