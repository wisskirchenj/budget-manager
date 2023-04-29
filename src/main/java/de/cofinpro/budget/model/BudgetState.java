package de.cofinpro.budget.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class BudgetState implements Serializable {

    @Serial
    private static final long serialVersionUID = 10L;

    private final List<Purchase> purchases;
    private double balance;
}
