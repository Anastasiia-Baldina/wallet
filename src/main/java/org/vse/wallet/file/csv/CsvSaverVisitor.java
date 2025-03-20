package org.vse.wallet.file.csv;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.Operation;
import org.vse.wallet.data.DataElement;
import org.vse.wallet.file.DataVisitor;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.OperationRegistry;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvSaverVisitor implements DataVisitor {
    private static final String DELIMITER = ";";
    private PrintWriter writer;

    @Override
    public void visitBankAccount(BankAccount bankAccount) {
        writer.print(bankAccount.getId());
        printDelimiter();
        writer.print(bankAccount.getName());
        printDelimiter();
        writer.println(bankAccount.getBalance());
    }

    @Override
    public void visitBankAccountRegistry(BankAccountRegistry bankAccountRegistry) {
        saveRegistry(bankAccountRegistry.findAll(), "bank-account.csv");
    }

    private void saveRegistry(Iterable<? extends DataElement> registry, String filename) {
        try {
            writer = new PrintWriter(filename);
            for (var elem : registry) {
                elem.accept(this);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(writer != null) {
                writer.flush();
                writer.close();
                writer = null;
            }
        }
    }

    @Override
    public void visitCategory(Category category) {
        writer.print(category.getId());
        printDelimiter();
        writer.print(category.getType().name());
        printDelimiter();
        writer.print(category.getName());
        writer.println();
    }

    @Override
    public void visitCategoryRegistry(CategoryRegistry categoryRegistry) {
        saveRegistry(categoryRegistry.findAll(), "category.csv");
    }

    @Override
    public void visitOperation(Operation operation) {
        writer.print(operation.getId());
        printDelimiter();
        writer.print(operation.getType().name());
        printDelimiter();
        writer.print(operation.getBankAccountId());
        printDelimiter();
        writer.print(operation.getAmount());
        printDelimiter();
        writer.print(operation.getDate().getTime());
        printDelimiter();
        writer.print(operation.getDescription());
        printDelimiter();
        writer.print(operation.getCategoryId());
        writer.println();
    }

    @Override
    public void visitOperationRegistry(OperationRegistry operationRegistry) {
        saveRegistry(operationRegistry.findAll(), "operation.csv");
    }
    
    private void printDelimiter() {
        writer.print(DELIMITER);
    }
}
