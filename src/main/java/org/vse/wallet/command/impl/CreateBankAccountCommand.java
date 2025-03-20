package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.BankAccountFacade;

public class CreateBankAccountCommand implements Command {
    private final BankAccountFacade facade;

    public CreateBankAccountCommand(BankAccountFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.createAccount();
    }
}
