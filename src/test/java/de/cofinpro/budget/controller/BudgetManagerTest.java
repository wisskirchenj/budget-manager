package de.cofinpro.budget.controller;

import de.cofinpro.budget.io.ConsolePrinter;
import de.cofinpro.budget.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Scanner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class BudgetManagerIT {

    @Mock
    Scanner scanner;

    @Mock
    ConsolePrinter consolePrinter;

    private BudgetManager budgetManager;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager(consolePrinter, scanner);
    }

    @Test
    void whenIncomeAdded_BalanceIncreased() {
        when(scanner.nextLine()).thenReturn("1", "1000.0", "4", "0");
        budgetManager.run();
        verifyMenuTimes(3);
        verify(consolePrinter).printInfo("\nEnter income:");
        verify(consolePrinter).printWithPrice("\nBalance:", 1000.0);
        verify(consolePrinter).printInfo("Income was added!");
    }

    @Test
    void whenPurchaseAdded_BalanceSubtracted() {
        when(scanner.nextLine()).thenReturn("1", "1000.0", "2", "1", "Apples", "15.15", "5", "4", "0");
        budgetManager.run();
        verifyMenuTimes(4);
        verify(consolePrinter).printInfo("\nEnter income:");
        verify(consolePrinter).printWithPrice("\nBalance:", 984.85);
        verify(consolePrinter).printInfo("Income was added!");
    }

    @Test
    void whenPurchasesAdded_ShowListHasPurchasesInOrder() {
        when(scanner.nextLine()).thenReturn("2", "2", "shoes", "1000.0", "1", "Apples", "15.15", "5", "3", "5", "6", "0");
        budgetManager.run();
        verifyMenuTimes(3);
        verify(consolePrinter, times(2)).printInfo("\nEnter purchase name:");
        verify(consolePrinter, times(2)).printInfo("Enter its price:");
        verify(consolePrinter, times(2)).printInfo("Purchase was added!");
        verify(consolePrinter).printWithPrice("Total sum:", 1015.15);
        InOrder inOrder = Mockito.inOrder(consolePrinter);
        inOrder.verify(consolePrinter).printInfo(new Purchase("shoes", 1000.00).toString());
        inOrder.verify(consolePrinter).printInfo(new Purchase("Apples", 15.15).toString());
    }

    @Test
    void whenNoPurchasesAdded_ShowListEmpty() {
        when(scanner.nextLine()).thenReturn("3", "0");
        budgetManager.run();
        verifyMenuTimes(2);
        verify(consolePrinter).printInfo("\n{}", "The purchase list is empty");
    }

    @Test
    void whenPurchasedOverBalance_BalanceZero() {
        when(scanner.nextLine()).thenReturn("1", "10.0", "2", "1", "shoes", "150.0", "5", "4", "0");
        budgetManager.run();
        verifyMenuTimes(4);
        verify(consolePrinter).printInfo("Income was added!");
        verify(consolePrinter).printWithPrice("\nBalance:", 0.0);
    }

    private void verifyMenuTimes(int times) {
        verify(consolePrinter, times(times)).printInfo("""
                                                
                                                Choose your action:
                                                1) Add income
                                                2) Add purchase
                                                3) Show list of purchases
                                                4) Balance
                                                0) Exit""");
    }
}