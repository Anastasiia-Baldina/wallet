package org.vse.wallet.ui.editor;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.CategoryType;
import org.vse.wallet.data.Operation;
import org.vse.wallet.data.OperationType;
import org.vse.wallet.ui.Editor;
import org.vse.wallet.ui.UserInterface;
import org.vse.wallet.utils.Asserts;
import org.vse.wallet.utils.Dates;

import java.util.List;

public class OperationEditor implements Editor<Operation> {
    private final UserInterface ui;
    private final List<Category> categories;
    private final List<BankAccount> bankAccounts;

    public OperationEditor(UserInterface ui, List<Category> categories, List<BankAccount> bankAccounts) {
        this.ui = ui;
        this.categories = Asserts.notEmpty(categories, "categories");
        this.bankAccounts = Asserts.notEmpty(bankAccounts, "bank accounts");
    }

    @Override
    public Operation create(long id) {
        var builder = Operation.builder()
                .setId(id)
                .setBankAccountId(selectBankAccountId());
        var type = selectOperationType();
        builder.setType(type)
                .setCategoryId(selectCategoryId(type))
                .setDate(ui.readDate("Date"))
                .setDescription(ui.readString("Description"))
                .setAmount(ui.readInt("Amount"));
        return builder.build();
    }

    @Override
    public Operation edit(Operation op) {
        var type = selectOperationType();
        return op.toBuilder()
                .setType(type)
                .setBankAccountId(selectBankAccountId())
                .setCategoryId(selectCategoryId(type))
                .setDate(ui.readDate("Date ["+ Dates.format(op.getDate())+"]"))
                .setDescription(ui.readString("Description ["+op.getDescription()+"]"))
                .setAmount(ui.readInt("Amount ["+ op.getAmount()+"]"))
                .build();
    }

    @Override
    public Class<Operation> getType() {
        return Operation.class;
    }

    private OperationType selectOperationType() {
        int selected = ui.selectOption("Select type", OperationType.listNames());
        return OperationType.values()[selected];
    }

    private long selectCategoryId(OperationType type) {
        CategoryType categoryType = switch (type) {
            case INCOME -> CategoryType.INCOME;
            case EXPENSE -> CategoryType.EXPENSE;
        };
        var filtered = categories.stream()
                .filter(c -> c.getType() == categoryType)
                .toList();
        var filteredNames = filtered.stream()
                .map(Category::getName)
                .toList();
        int selected = ui.selectOption("Select category", filteredNames);
        return filtered.get(selected).getId();
    }

    long selectBankAccountId() {
        var accountNames = bankAccounts.stream().map(BankAccount::getName).toList();
        int selected = ui.selectOption("Select bank account", accountNames);
        return bankAccounts.get(selected).getId();
    }
}
