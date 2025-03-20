package org.vse.wallet.file;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.Operation;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.OperationRegistry;

public interface DataVisitor {

    void visitBankAccount(BankAccount bankAccount);

    void visitBankAccountRegistry(BankAccountRegistry bankAccountRegistry);

    void visitCategory(Category category);

    void visitCategoryRegistry(CategoryRegistry categoryRegistry);

    void visitOperation(Operation operation);

    void visitOperationRegistry(OperationRegistry operationRegistry);
}
