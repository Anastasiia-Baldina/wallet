package org.vse.wallet.facade.impl;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.facade.BankAccountFacade;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.ui.Editor;
import org.vse.wallet.ui.EditorFactory;
import org.vse.wallet.ui.UserInterface;
import org.vse.wallet.ui.editor.EditorType;
import org.vse.wallet.utils.Ids;

import javax.annotation.Nullable;

public class BankAccountFacadeImpl implements BankAccountFacade {
    private final BankAccountRegistry bankAccountRegistry;
    private final EditorFactory editorFactory;
    private final UserInterface ui;

    public BankAccountFacadeImpl(BankAccountRegistry bankAccountRegistry,
                                 EditorFactory editorFactory,
                                 UserInterface ui) {
        this.bankAccountRegistry = bankAccountRegistry;
        this.editorFactory = editorFactory;
        this.ui = ui;
    }

    @Override
    public void listAccounts() {
        var data = bankAccountRegistry.findAll();
        if(data.isEmpty()) {
            ui.printLine("\tNo bank accounts found.");
        } else {
            ui.printLine("Bank accounts:");
            data.forEach(account -> {
                ui.printLine(account.toString());
            });
        }
    }

    @Override
    public void createAccount() {
        Editor<BankAccount> editor = editorFactory.createEditor(EditorType.BANK_ACCOUNT);
        BankAccount newAccount = editor.create(Ids.generateId());
        bankAccountRegistry.insert(newAccount);
    }

    @Override
    public void editAccount() {
        var account = selectBankAccount();
        if(account != null) {
            Editor<BankAccount> editor = editorFactory.createEditor(EditorType.BANK_ACCOUNT);
            var newAccount = editor.edit(account);
            bankAccountRegistry.update(newAccount);
        }
    }

    @Override
    public void deleteAccount() {
        var account = selectBankAccount();
        if(account != null) {
            bankAccountRegistry.removeById(account.getId());
        }
    }

    @Nullable
    private BankAccount selectBankAccount() {
        var accounts = bankAccountRegistry.findAll();
        if(accounts.isEmpty()) {
            ui.printLine("\tNo bank accounts found");
            return null;
        }
        var accountNames = accounts.stream()
                .map(x -> "\t" + x.getId() + " "+x.getName())
                .toList();
        int selected = ui.selectOption("Select bank account", accountNames);
        return accounts.get(selected);
    }
}
