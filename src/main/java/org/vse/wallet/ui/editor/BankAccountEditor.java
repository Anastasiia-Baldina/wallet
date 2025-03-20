package org.vse.wallet.ui.editor;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.ui.Editor;
import org.vse.wallet.ui.UserInterface;

public class BankAccountEditor implements Editor<BankAccount> {
    private final UserInterface ui;

    public BankAccountEditor(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public BankAccount create(long id) {
        return BankAccount.builder()
                .setId(id)
                .setName(ui.readString("Name"))
                .build();
    }

    @Override
    public BankAccount edit(BankAccount entity) {
        return entity.toBuilder()
                .setName(ui.readString("Name [" + entity.getName() + "]"))
                .build();
    }

    @Override
    public Class<BankAccount> getType() {
        return BankAccount.class;
    }
}
