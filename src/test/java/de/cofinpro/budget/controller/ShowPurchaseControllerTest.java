package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import static de.cofinpro.budget.controller.BudgetManager.EMPTY_PURCHASES;
import static org.mockito.Mockito.verify;

@MockitoSettings
class ShowPurchaseControllerTest {

    static {
        Locale.setDefault(Locale.US);
    }

    @Mock
    ConsolePrinter printerMock;

    @Mock
    Scanner scannerMock;

    List<Purchase> purchases;

    ShowPurchaseController showPurchaseController;

    @BeforeEach
    void setUp() {
        purchases = new ArrayList<>(List.of(new Purchase("Shoes", Category.CLOTHES, 125.50)));
        showPurchaseController = new ShowPurchaseController(printerMock, scannerMock, purchases);
    }

    static Stream<Arguments> whenChoiceGiven_categoryResolvedAndListCorrect() {
        return Stream.of(
                Arguments.of(ShowPurchaseController.Choice.FOOD, EMPTY_PURCHASES, "Food"),
                Arguments.of(ShowPurchaseController.Choice.CLOTHES, "Shoes $125.50", "Clothes"),
                Arguments.of(ShowPurchaseController.Choice.ENTERTAINMENT, EMPTY_PURCHASES, "Entertainment"),
                Arguments.of(ShowPurchaseController.Choice.OTHER, EMPTY_PURCHASES, "Other"),
                Arguments.of(ShowPurchaseController.Choice.ALL, "Shoes $125.50", "All")
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenChoiceGiven_categoryResolvedAndListCorrect(ShowPurchaseController.Choice choice, String purchaseMsg, String cat) {
        var action = showPurchaseController.getMenuAction(choice);
        action.run();
        verify(printerMock).printInfo(purchaseMsg);
        verify(printerMock).printInfo("\n{}:", cat);
    }
}