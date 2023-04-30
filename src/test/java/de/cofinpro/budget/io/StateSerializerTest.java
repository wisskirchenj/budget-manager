package de.cofinpro.budget.io;

import de.cofinpro.budget.model.BudgetState;
import de.cofinpro.budget.model.Category;
import de.cofinpro.budget.model.Purchase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateSerializerTest {

    private static final Path DIR = Path.of("src/main/resources/data");
    private StateSerializer serializer;
    private BudgetState state;

    @BeforeAll
    static void createDirIfNotExists() throws IOException {
        if (!Files.exists(DIR)) {
            Files.createDirectory(DIR);
        }
    }

    @BeforeEach
    void setup() {
        serializer = new StateSerializer(null);
        state = new BudgetState(new ArrayList<>(List.of(new Purchase("shoes", Category.CLOTHES, 200.50))));
        state.setBalance(500);
    }

    @Test
    void whenSerialize_stateSavedDeserializeRestores() {
        serializer.serialize(state);
        var restored = serializer.deserialize();
        assertEquals(state, restored);
    }

}