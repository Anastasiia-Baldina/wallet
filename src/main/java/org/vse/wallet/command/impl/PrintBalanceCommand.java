package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.ManagerFacade;

public class PrintBalanceCommand implements Command {
    private final ManagerFacade managerFacade;

    public PrintBalanceCommand(ManagerFacade managerFacade) {
        this.managerFacade = managerFacade;
    }

    @Override
    public void execute() {
        managerFacade.printPeriodBalance();
    }
}
