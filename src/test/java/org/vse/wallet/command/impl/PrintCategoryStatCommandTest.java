package org.vse.wallet.command.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.facade.ManagerFacade;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PrintCategoryStatCommandTest {
    @Mock
    private ManagerFacade managerFacade;
    @InjectMocks
    private PrintCategoryStatCommand command;

    @Test
    public void testExecute() {
        command.execute();
        verify(managerFacade).printCategoryStats();
    }
}