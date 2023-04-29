package de.cofinpro.budget.io;

import de.cofinpro.budget.model.BudgetState;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StateSerializer {

    private static final String SERIALIZE_PATH = "src/main/resources/data/budget-state.ser";

    private final ConsolePrinter printer;

    public StateSerializer(ConsolePrinter printer) {
        this.printer = printer;
    }

    /**
     * serialization of the budget state via ObjectOutputStream.writeObject
     * @param budgetState budget manager application state to serialize
     */
    public void serialize(BudgetState budgetState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(SERIALIZE_PATH)))) {
            oos.writeObject(budgetState);
        } catch (IOException exception) {
            printer.printError("cannot serialize to file\n", exception);
        }
    }

    public BudgetState deserialize() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(SERIALIZE_PATH)))) {
            return (BudgetState) ois.readObject();
        } catch (FileNotFoundException exception) {
            printer.printInfo("No serialization data found.");
        } catch (IOException exception) {
            printer.printError("IO-error reading deserialization file!\n", exception);
        } catch (ClassNotFoundException exception) {
            printer.printError("cannot deserialize file. File corrupted!\n", exception);
        }
        return new BudgetState(new ArrayList<>());
    }
}
