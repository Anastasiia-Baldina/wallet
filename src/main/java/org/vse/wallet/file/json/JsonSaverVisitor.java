package org.vse.wallet.file.json;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.DataElement;
import org.vse.wallet.data.Operation;
import org.vse.wallet.file.DataVisitor;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.OperationRegistry;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonSaverVisitor implements DataVisitor {
    private static final String DELIMITER = ",";
    private PrintWriter writer;

    @Override
    public void visitBankAccount(BankAccount bankAccount) {
        printField("id", bankAccount.getId());
        printDelimiter();
        printField("name", bankAccount.getName());
        printDelimiter();
        printField("balance", bankAccount.getBalance());
        writer.println();
    }

    @Override
    public void visitBankAccountRegistry(BankAccountRegistry bankAccountRegistry) {
        saveRegistry(bankAccountRegistry.findAll(), "bank-account.json");
    }

    @Override
    public void visitCategory(Category category) {
        printField("id", category.getId());
        printDelimiter();
        printField("type", category.getType().name());
        printDelimiter();
        printField("name", category.getName());
        writer.println();
    }

    @Override
    public void visitCategoryRegistry(CategoryRegistry categoryRegistry) {
        saveRegistry(categoryRegistry.findAll(), "category.json");
    }

    @Override
    public void visitOperation(Operation operation) {
        printField("id", operation.getId());
        printDelimiter();
        printField("type", operation.getType().name());
        printDelimiter();
        printField("bankAccountId", operation.getBankAccountId());
        printDelimiter();
        printField("amount", operation.getAmount());
        printDelimiter();
        printField("date", operation.getDate().getTime());
        printDelimiter();
        printField("description", operation.getDescription());
        printDelimiter();
        printField("categoryId", operation.getCategoryId());
        writer.println();
    }

    @Override
    public void visitOperationRegistry(OperationRegistry operationRegistry) {
        saveRegistry(operationRegistry.findAll(), "operation.json");
    }

    private void saveRegistry(Iterable<? extends DataElement> registry, String filename) {
        try {
            writer = new PrintWriter(filename);
            var it = registry.iterator();
            writer.println("[");
            while (it.hasNext()) {
                var elem = it.next();
                writer.println("\t{");
                elem.accept(this);
                if (it.hasNext()) {
                    writer.println("\t},");
                } else {
                    writer.println("\t}");
                }
            }
            writer.println("]");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
                writer = null;
            }
        }
    }

    private void printField(String fieldName, String fieldValue) {
        var indent = "\t\t";
        if (fieldValue == null) {
            writer.print(indent + "\"" + fieldName + "\": null");
        } else {
            writer.print(indent + "\"" +fieldName + "\": \"" + fieldValue + '"');
        }
    }

    private void printField(String fieldName, Object fieldValue) {
        var indent = "\t\t";
        if (fieldValue == null) {
            writer.print(indent + "\"" +fieldName + "\": null");
        } else {
            writer.print(indent + "\"" + fieldName + "\": " + fieldValue);
        }
    }

    private void printDelimiter() {
        writer.println(DELIMITER);
    }
}
