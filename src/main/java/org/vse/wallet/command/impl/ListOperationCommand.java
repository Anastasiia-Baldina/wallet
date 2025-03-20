package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.OperationFacade;

public class ListOperationCommand implements Command {
    private final OperationFacade facade;

    public ListOperationCommand(OperationFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.listOperations();
    }
}
