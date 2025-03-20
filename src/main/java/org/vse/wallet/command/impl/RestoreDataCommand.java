package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.ManagerFacade;

public class RestoreDataCommand implements Command {
    private final ManagerFacade managerFacade;

    public RestoreDataCommand(ManagerFacade managerFacade) {
        this.managerFacade = managerFacade;
    }

    @Override
    public void execute() {
        managerFacade.restoreData();
    }
}
