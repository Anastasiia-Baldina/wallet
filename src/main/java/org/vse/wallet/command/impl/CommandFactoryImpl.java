package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.command.CommandFactory;
import org.vse.wallet.command.CommandType;
import org.vse.wallet.facade.BankAccountFacade;
import org.vse.wallet.facade.CategoryFacade;
import org.vse.wallet.facade.ManagerFacade;
import org.vse.wallet.facade.OperationFacade;

public class CommandFactoryImpl implements CommandFactory {
    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;
    private final ManagerFacade managerFacade;

    public CommandFactoryImpl(BankAccountFacade bankAccountFacade,
                              CategoryFacade categoryFacade,
                              OperationFacade operationFacade,
                              ManagerFacade managerFacade) {
        this.bankAccountFacade = bankAccountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
        this.managerFacade = managerFacade;
    }

    @Override
    public Command createCommand(CommandType cmdType) {
        return switch (cmdType) {
            case LIST_BANK_ACCOUNT -> new ListBankAccountCommand(bankAccountFacade);
            case EDIT_BANK_ACCOUNT -> new EditBankAccountCommand(bankAccountFacade);
            case CREATE_BANK_ACCOUNT -> new CreateBankAccountCommand(bankAccountFacade);
            case DELETE_BANK_ACCOUNT -> new DeleteBankAccountCommand(bankAccountFacade);
            case RECALCULATE_BANK_ACCOUNT -> new RecalculateBalanceCommand(managerFacade);
            case PRINT_BALANCE -> new PrintBalanceCommand(managerFacade);
            case PRINT_CATEGORY_STATS -> new PrintCategoryStatCommand(managerFacade);
            case LIST_CATEGORY -> new ListCategoryCommand(categoryFacade);
            case EDIT_CATEGORY -> new EditCategoryCommand(categoryFacade);
            case CREATE_CATEGORY -> new CreateCategoryCommand(categoryFacade);
            case DELETE_CATEGORY -> new DeleteCategoryCommand(categoryFacade);
            case LIST_OPERATION -> new ListOperationCommand(operationFacade);
            case EDIT_OPERATION -> new EditOperationCommand(operationFacade);
            case CREATE_OPERATION -> new CreateOperationCommand(operationFacade);
            case DELETE_OPERATION -> new DeleteOperationCommand(operationFacade);
            case BACKUP -> new BackupDataCommand(managerFacade);
            case RESTORE -> new RestoreDataCommand(managerFacade);
        };
    }
}
