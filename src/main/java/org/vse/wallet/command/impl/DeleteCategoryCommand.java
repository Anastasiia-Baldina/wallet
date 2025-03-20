package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.CategoryFacade;

public class DeleteCategoryCommand implements Command {
    private final CategoryFacade facade;

    public DeleteCategoryCommand(CategoryFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.deleteCategory();
    }
}
