package org.vse.wallet.command.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.facade.OperationFacade;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteOperationCommandTest {
    @Mock
    private OperationFacade facade;
    @InjectMocks
    private DeleteOperationCommand command;

    @Test
    public void testExecute() {
        command.execute();
        verify(facade).deleteOperation();
    }
}