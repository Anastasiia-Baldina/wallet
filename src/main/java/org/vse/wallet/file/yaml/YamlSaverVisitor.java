package org.vse.wallet.file.yaml;

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

public class YamlSaverVisitor implements DataVisitor {
    private PrintWriter writer;

    @Override
    public void visitBankAccount(BankAccount bankAccount) {
        printField("id", bankAccount.getId());
        printIndent();
        printField("name", bankAccount.getName());
        printIndent();
        printField("balance", bankAccount.getBalance());
    }

    @Override
    public void visitBankAccountRegistry(BankAccountRegistry bankAccountRegistry) {
        saveRegistry(bankAccountRegistry.findAll(), "bank-account.yaml");
    }

    @Override
    public void visitCategory(Category category) {
        printField("id", category.getId());
        printIndent();
        printField("type", category.getType().name());
        printIndent();
        printField("name", category.getName());
    }

    @Override
    public void visitCategoryRegistry(CategoryRegistry categoryRegistry) {
        saveRegistry(categoryRegistry.findAll(), "category.yaml");
    }

    @Override
    public void visitOperation(Operation operation) {
        printField("id", operation.getId());
        printIndent();
        printField("type", operation.getType().name());
        printIndent();
        printField("bankAccountId", operation.getBankAccountId());
        printIndent();
        printField("amount", operation.getAmount());
        printIndent();
        printField("date", operation.getDate().getTime());
        printIndent();
        printField("description", operation.getDescription());
        printIndent();
        printField("categoryId", operation.getCategoryId());
    }

    @Override
    public void visitOperationRegistry(OperationRegistry operationRegistry) {
        saveRegistry(operationRegistry.findAll(), "operation.yaml");
    }

    private void saveRegistry(Iterable<? extends DataElement> registry, String filename) {
        try {
            writer = new PrintWriter(filename);
            for (var elem : registry) {
                writer.print("- ");
                elem.accept(this);
            }
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
        if (fieldValue == null) {
            writer.println(fieldName + ": null");
        } else {
            writer.println(fieldName + ": \"" + fieldValue + '"');
        }
    }

    private void printField(String fieldName, Object fieldValue) {
        if (fieldValue == null) {
            writer.println(fieldName + ": null");
        } else {
            writer.println(fieldName + ": " + fieldValue);
        }
    }

    private void printIndent() {
        writer.print("  ");
    }
}
