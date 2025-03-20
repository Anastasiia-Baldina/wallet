package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.facade.CategoryFacade;

public class EditCategoryCommand implements Command {
    private final CategoryFacade facade;

    public EditCategoryCommand(CategoryFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.editCategory();
    }
}
