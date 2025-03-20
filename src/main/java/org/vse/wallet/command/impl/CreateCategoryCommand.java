package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.CategoryFacade;

public class CreateCategoryCommand implements Command {
    private final CategoryFacade facade;

    public CreateCategoryCommand(CategoryFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.createCategory();
    }
}
