package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.BankAccountFacade;

public class EditBankAccountCommand implements Command {
    private final BankAccountFacade facade;

    public EditBankAccountCommand(BankAccountFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.editAccount();
    }
}
