package org.vse.wallet.command.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.command.Command;
import org.vse.wallet.command.CommandType;
import org.vse.wallet.facade.BankAccountFacade;
import org.vse.wallet.facade.CategoryFacade;
import org.vse.wallet.facade.ManagerFacade;
import org.vse.wallet.facade.OperationFacade;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CommandFactoryImplTest {
    @Mock
    private BankAccountFacade bankAccountFacade;
    @Mock
    private CategoryFacade categoryFacade;
    @Mock
    private OperationFacade operationFacade;
    @Mock
    private ManagerFacade managerFacade;
    @InjectMocks
    private CommandFactoryImpl commandFactory;

    @Test
    public void testCreateCommand() {
        Command command = commandFactory.createCommand(CommandType.LIST_BANK_ACCOUNT);
        assertInstanceOf(ListBankAccountCommand.class, command);

        command = commandFactory.createCommand(CommandType.EDIT_BANK_ACCOUNT);
        assertInstanceOf(EditBankAccountCommand.class, command);

        command = commandFactory.createCommand(CommandType.CREATE_BANK_ACCOUNT);
        assertInstanceOf(CreateBankAccountCommand.class, command);

        command = commandFactory.createCommand(CommandType.DELETE_BANK_ACCOUNT);
        assertInstanceOf(DeleteBankAccountCommand.class, command);

        command = commandFactory.createCommand(CommandType.RECALCULATE_BANK_ACCOUNT);
        assertInstanceOf(RecalculateBalanceCommand.class, command);

        command = commandFactory.createCommand(CommandType.PRINT_BALANCE);
        assertInstanceOf(PrintBalanceCommand.class, command);

        command = commandFactory.createCommand(CommandType.PRINT_CATEGORY_STATS);
        assertInstanceOf(PrintCategoryStatCommand.class, command);

        command = commandFactory.createCommand(CommandType.LIST_CATEGORY);
        assertInstanceOf(ListCategoryCommand.class, command);

        command = commandFactory.createCommand(CommandType.EDIT_CATEGORY);
        assertInstanceOf(EditCategoryCommand.class, command);

        command = commandFactory.createCommand(CommandType.CREATE_CATEGORY);
        assertInstanceOf(CreateCategoryCommand.class, command);

        command = commandFactory.createCommand(CommandType.DELETE_CATEGORY);
        assertInstanceOf(DeleteCategoryCommand.class, command);

        command = commandFactory.createCommand(CommandType.LIST_OPERATION);
        assertInstanceOf(ListOperationCommand.class, command);

        command = commandFactory.createCommand(CommandType.EDIT_OPERATION);
        assertInstanceOf(EditOperationCommand.class, command);

        command = commandFactory.createCommand(CommandType.CREATE_OPERATION);
        assertInstanceOf(CreateOperationCommand.class, command);

        command = commandFactory.createCommand(CommandType.DELETE_OPERATION);
        assertInstanceOf(DeleteOperationCommand.class, command);

        command = commandFactory.createCommand(CommandType.BACKUP);
        assertInstanceOf(BackupDataCommand.class, command);

        command = commandFactory.createCommand(CommandType.RESTORE);
        assertInstanceOf(RestoreDataCommand.class, command);
    }
}