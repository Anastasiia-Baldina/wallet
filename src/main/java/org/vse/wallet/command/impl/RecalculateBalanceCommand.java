package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.ManagerFacade;

public class RecalculateBalanceCommand implements Command {
    private final ManagerFacade facade;

    public RecalculateBalanceCommand(ManagerFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.recalculateBalance();
    }
}
