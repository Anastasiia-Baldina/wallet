package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.BankAccountFacade;

public class DeleteBankAccountCommand implements Command {
    private final BankAccountFacade facade;

    public DeleteBankAccountCommand(BankAccountFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.deleteAccount();
    }
}
