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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class AnalyzeControllerTest {

    static {
        Locale.setDefault(Locale.US);
    }

    @Mock
    ConsolePrinter printerMock;

    @Mock
    Scanner scannerMock;

    AnalyzeController analyzeController;
    List<Purchase> purchases = List.of(
            new Purchase("Gum", Category.FOOD, 0.79),
            new Purchase("TV", Category.ENTERTAINMENT, 1459.99),
            new Purchase("Bananas", Category.FOOD, 2.79),
            new Purchase("Shoes", Category.CLOTHES, 144.49)
    );

    @BeforeEach
    void setUp() {
        analyzeController = new AnalyzeController(printerMock, scannerMock, purchases);
    }

    public static Stream<Arguments> whenChoiceMade_appropriateActionCalled() {
        return Stream.of(
                Arguments.of(new String[] {"1", "4"}, "All"),
                Arguments.of(new String[] {"3", "1", "4"}, "Food"),
                Arguments.of(new String[] {"3", "2", "4"}, "Clothes"),
                Arguments.of(new String[] {"3", "3", "4"}, "Entertainment"),
                Arguments.of(new String[] {"3", "4", "4"}, "Other")
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenChoiceMade_appropriateActionCalled(String[] choice, String expectedCategory) {
        when(scannerMock.nextLine()).thenReturn(choice[0], Arrays.copyOfRange(choice, 1, choice.length));
        analyzeController.run();
        verify(printerMock).printInfo("\n{}:", expectedCategory);
    }

    @Test
    void whenSortByCategoryChosen_OutputGroupedSortedAndAggregated() {
        when(scannerMock.nextLine()).thenReturn("2", "4");
        analyzeController.run();
        InOrder inOrder = Mockito.inOrder(printerMock);
        inOrder.verify(printerMock).printInfo("\nTypes:");
        inOrder.verify(printerMock).printInfo("Entertainment - $1459.99");
        inOrder.verify(printerMock).printInfo("Clothes - $144.49");
        inOrder.verify(printerMock).printInfo("Food - $3.58");
        inOrder.verify(printerMock).printWithPrice("Total sum:", 1608.06);
    }
}