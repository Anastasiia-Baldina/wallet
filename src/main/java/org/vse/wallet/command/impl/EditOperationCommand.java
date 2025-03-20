package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.OperationFacade;

public class EditOperationCommand implements Command {
    private final OperationFacade facade;

    public EditOperationCommand(OperationFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.editOperation();
    }
}
