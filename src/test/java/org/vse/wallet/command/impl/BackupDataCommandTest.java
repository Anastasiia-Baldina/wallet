package org.vse.wallet.command.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.facade.ManagerFacade;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BackupDataCommandTest {
    @Mock
    private ManagerFacade managerFacade;
    @InjectMocks
    private BackupDataCommand backupDataCommand;

    @Test
    public void testExecute() {
        backupDataCommand.execute();
        verify(managerFacade).backupData();
    }
}