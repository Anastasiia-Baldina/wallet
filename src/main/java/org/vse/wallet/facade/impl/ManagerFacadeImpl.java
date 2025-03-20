package org.vse.wallet.facade.impl;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.Operation;
import org.vse.wallet.facade.ManagerFacade;
import org.vse.wallet.file.DataLoaderFactory;
import org.vse.wallet.file.DataSaver;
import org.vse.wallet.file.Format;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.OperationRegistry;
import org.vse.wallet.ui.UserInterface;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ManagerFacadeImpl implements ManagerFacade {
    private final BankAccountRegistry bankAccountRegistry;
    private final CategoryRegistry categoryRegistry;
    private final OperationRegistry operationRegistry;
    private final UserInterface ui;
    private final DataSaver dataSaver;
    private final DataLoaderFactory dataLoaderFactory;

    public ManagerFacadeImpl(BankAccountRegistry bankAccountRegistry,
                             CategoryRegistry categoryRegistry,
                             OperationRegistry operationRegistry,
                             UserInterface ui,
                             DataSaver dataSaver, DataLoaderFactory dataLoaderFactory) {
        this.bankAccountRegistry = bankAccountRegistry;
        this.categoryRegistry = categoryRegistry;
        this.operationRegistry = operationRegistry;
        this.ui = ui;
        this.dataSaver = dataSaver;
        this.dataLoaderFactory = dataLoaderFactory;
    }

    @Override
    public void recalculateBalance() {
        var bankAccounts = bankAccountRegistry.findAll();
        var accountNames = bankAccounts.stream()
                .map(BankAccount::getName)
                .toList();
        int selected = ui.selectOption("Select bank account", accountNames);
        var account = bankAccounts.get(selected);
        if (account != null) {
            long newBalance = operationRegistry.findByBankAccountId(account.getId()).stream()
                    .mapToLong(Operation::getAmount)
                    .sum();
            bankAccountRegistry.update(
                    account.toBuilder()
                            .setBalance(newBalance)
                            .build()
            );
        } else {
            throw new IllegalStateException("Bank account not found");
        }
    }

    @Override
    public void printPeriodBalance() {
        var startDate = ui.readDate("Enter start date");
        var endDate = ui.readDate("Enter end date");
        long balance = operationRegistry.findAll().stream()
                .filter(op -> op.getDate().getTime() >= startDate.getTime())
                .filter(op -> op.getDate().getTime() <= endDate.getTime())
                .mapToLong(Operation::getAmount)
                .sum();
        ui.printLine("\tBalance: " + balance);
    }

    @Override
    public void printCategoryStats() {
        var startDate = ui.readDate("Enter start date");
        var endDate = ui.readDate("Enter end date");
        var balances = operationRegistry.findAll().stream()
                .filter(op -> op.getDate().getTime() >= startDate.getTime())
                .filter(op -> op.getDate().getTime() <= endDate.getTime())
                .collect(Collectors.groupingBy(Operation::getCategoryId, Collectors.summingLong(Operation::getAmount)));
        balances.forEach((catId, balance) -> {
            var category = categoryRegistry.findById(catId);
            var categoryName = category == null ? "Unknown" : category.getName();
            ui.printLine("\t " + categoryName + ": " + balance);
        });
    }

    @Override
    public void backupData() {
        var format = requestFormat();
        dataSaver.save(bankAccountRegistry, format);
        ui.printLine("\t Bank accounts saved ...");
        dataSaver.save(categoryRegistry, format);
        ui.printLine("\t Categories saved ...");
        dataSaver.save(operationRegistry, format);
        ui.printLine("\t Operations saved ...");
        ui.printLine("\t Backup completed");
    }

    @Override
    public void restoreData() {
        var format = requestFormat();
        bankAccountRegistry.removeAll();
        ui.printLine("\t Bank accounts removed ...");
        dataLoaderFactory.createDataLoader(format, BankAccount.class).load().forEach(bankAccountRegistry::insert);
        ui.printLine("\t Bank accounts restored from file ...");
        categoryRegistry.removeAll();
        ui.printLine("\t Categories removed ...");
        dataLoaderFactory.createDataLoader(format, Category.class).load().forEach(categoryRegistry::insert);
        ui.printLine("\t Categories restored from file ...");
        operationRegistry.removeAll();
        ui.printLine("\t Operations removed ...");
        dataLoaderFactory.createDataLoader(format, Operation.class).load().forEach(operationRegistry::insert);
        ui.printLine("\t Operations restored from file ...");
    }

    private Format requestFormat() {
        var formatNames = Arrays.stream(Format.values())
                .map(Format::name)
                .toList();
        int selected = ui.selectOption("Select backup format", formatNames);
        return Format.values()[selected];
    }
}
