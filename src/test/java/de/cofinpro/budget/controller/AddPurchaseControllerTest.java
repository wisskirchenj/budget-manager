package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@MockitoSettings
class AddPurchaseControllerTest {

    @Mock
    private ConsolePrinter printerMock;

    @Mock
    private Scanner scannerMock;

    private AddPurchaseController addPurchaseController;
    private List<Purchase> purchases;


    @BeforeEach
    void setUp() {
        purchases = new ArrayList<>(List.of(new Purchase("shoes", Category.CLOTHES, 175.50)));
        addPurchaseController = new AddPurchaseController(printerMock, scannerMock, purchases);
    }

    @Test
    void whenBackChosen_purchasesUnchanged() {
        when(scannerMock.nextLine()).thenReturn("5"); //Back
        addPurchaseController.run();
        assertEquals(1, purchases.size());
    }

    @Test
    void whenClothesPurchaseAdded_purchasesGets2ndClothesEntry() {
        when(scannerMock.nextLine()).thenReturn("2", "Blouse", "56.79", "5");
        addPurchaseController.run();
        InOrder inOrder = Mockito.inOrder(printerMock);
        inOrder.verify(printerMock).printInfo(addPurchaseController.getMenuText());
        inOrder.verify(printerMock).printInfo("\nEnter purchase name:");
        inOrder.verify(printerMock).printInfo("Enter its price:");
        inOrder.verify(printerMock).printInfo("Purchase was added!");
        assertEquals(2, purchases.size());
        assertEquals(Category.CLOTHES, purchases.get(1).category());
        assertEquals(56.79, purchases.get(1).price());
        assertEquals("Blouse", purchases.get(1).item());
    }

    static Stream<Arguments> whenChoiceGiven_categoryResolved() {
        return Stream.of(
                Arguments.of(AddPurchaseController.Choice.FOOD, Category.FOOD),
                Arguments.of(AddPurchaseController.Choice.CLOTHES, Category.CLOTHES),
                Arguments.of(AddPurchaseController.Choice.ENTERTAINMENT, Category.ENTERTAINMENT),
                Arguments.of(AddPurchaseController.Choice.OTHER, Category.OTHER)
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenChoiceGiven_categoryResolved(AddPurchaseController.Choice choice, Category category) {
        var action = addPurchaseController.getMenuAction(choice);
        when(scannerMock.nextLine()).thenReturn("Test", "1.0");
        action.run();
        assertEquals(category, purchases.get(1).category());
    }
}