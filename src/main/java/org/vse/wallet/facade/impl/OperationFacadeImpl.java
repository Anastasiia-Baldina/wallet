package org.vse.wallet.facade.impl;

import org.vse.wallet.data.Operation;
import org.vse.wallet.facade.OperationFacade;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.OperationRegistry;
import org.vse.wallet.ui.Editor;
import org.vse.wallet.ui.EditorFactory;
import org.vse.wallet.ui.UserInterface;
import org.vse.wallet.ui.editor.EditorType;
import org.vse.wallet.utils.Dates;
import org.vse.wallet.utils.Ids;

import java.util.Comparator;
import java.util.stream.Collectors;

public class OperationFacadeImpl implements OperationFacade {
    private final EditorFactory editorFactory;
    private final OperationRegistry operationRegistry;
    private final BankAccountRegistry bankAccountRegistry;
    private final CategoryRegistry categoryRegistry;
    private final UserInterface ui;

    public OperationFacadeImpl(EditorFactory editorFactory,
                               OperationRegistry operationRegistry,
                               BankAccountRegistry bankAccountRegistry,
                               CategoryRegistry categoryRegistry,
                               UserInterface ui) {
        this.editorFactory = editorFactory;
        this.operationRegistry = operationRegistry;
        this.bankAccountRegistry = bankAccountRegistry;
        this.categoryRegistry = categoryRegistry;
        this.ui = ui;
    }

    @Override
    public void listOperations() {
        var opGroups = operationRegistry.findAll().stream()
                .sorted(Comparator.comparingLong(Operation::getId))
                .collect(Collectors.groupingBy(Operation::getBankAccountId));

        if (opGroups.isEmpty()) {
            ui.printLine("No operations found.");
        } else {
            opGroups.forEach((accountId, operations) -> {
                var account = bankAccountRegistry.findById(accountId);
                var accountName = account != null ? accountId + " " + account.getName() : "not found";
                ui.printLine("\tBank account: " + accountName);
                operations.forEach(op -> {
                    var category = categoryRegistry.findById(op.getCategoryId());
                    var categoryName = category != null ? category.getName() : "not found";
                    var opInfo = "\t\tId: %s, Date: %s, Category: %s, Type: %s, Info: %s, Amount: %s".formatted(
                            op.getId(),
                            Dates.format(op.getDate()),
                            categoryName,
                            op.getType(),
                            op.getDescription() == null ? "" : op.getDescription(),
                            op.getAmount());
                    ui.printLine(opInfo);
                });
            });
        }
    }

    @Override
    public void createOperation() {
        Editor<Operation> editor = editorFactory.createEditor(EditorType.OPERATION);
        var newOp = editor.create(Ids.generateId());
        operationRegistry.insert(newOp);
        var account = bankAccountRegistry.findById(newOp.getBankAccountId());
        if (account != null) {
            var newAccount = account.toBuilder()
                    .setBalance(account.getBalance() + newOp.getAmount())
                    .build();
            bankAccountRegistry.update(newAccount);
        }
    }

    @Override
    public void editOperation() {
        long opId = ui.readLong("Operation id");
        var op = operationRegistry.findById(opId);
        if (op == null) {
            ui.printLine("Operation with id " + opId + " not found");
        } else {
            Editor<Operation> editor = editorFactory.createEditor(EditorType.OPERATION);
            var newOp = editor.edit(op);
            operationRegistry.update(newOp);
            var oldAccount = bankAccountRegistry.findById(op.getBankAccountId());
            if(oldAccount != null) {
                var newAccount = oldAccount.toBuilder()
                        .setBalance(oldAccount.getBalance() - newOp.getAmount())
                        .build();
                bankAccountRegistry.update(newAccount);
            }
            var account = bankAccountRegistry.findById(newOp.getBankAccountId());
            if (account != null) {
                var newAccount = account.toBuilder()
                        .setBalance(account.getBalance() + newOp.getAmount())
                        .build();
                bankAccountRegistry.update(newAccount);
            }
        }
    }

    @Override
    public void deleteOperation() {
        long opId = ui.readLong("Operation id");
        var op = operationRegistry.findById(opId);
        if (op == null) {
            ui.printLine("Operation with id " + opId + " not found");
        } else {
            operationRegistry.removeById(opId);
            var account = bankAccountRegistry.findById(op.getBankAccountId());
            if (account != null) {
                var newAccount = account.toBuilder()
                        .setBalance(account.getBalance() - op.getAmount())
                        .build();
                bankAccountRegistry.update(newAccount);
            }
        }
    }
}
