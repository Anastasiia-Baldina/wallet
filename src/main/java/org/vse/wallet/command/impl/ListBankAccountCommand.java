package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.BankAccountFacade;

public class ListBankAccountCommand implements Command {
    private final BankAccountFacade facade;

    public ListBankAccountCommand(BankAccountFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.listAccounts();
    }
}
