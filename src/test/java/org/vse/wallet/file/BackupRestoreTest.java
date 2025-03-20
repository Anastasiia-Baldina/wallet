package org.vse.wallet.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.vse.wallet.data.BankAccount;
import org.vse.wallet.di.spring.ApplicationConfiguration;
import org.vse.wallet.di.spring.ServiceConfiguration;
import org.vse.wallet.facade.ManagerFacade;
import org.vse.wallet.ui.UserInterface;

import javax.sql.DataSource;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(
        classes = {
                ServiceConfiguration.class,
                ApplicationConfiguration.class,
                BackupRestoreTest.MockUI.class,
                BackupRestoreTest.DataSourceConfiguration.class
        },
        loader = AnnotationConfigContextLoader.class)
public class BackupRestoreTest {
    @Autowired
    private ManagerFacade managerFacade;
    @Autowired
    private UserInterface ui;

    @Test
    public void testBackupRestoreCsv() {
        when(ui.selectOption(eq("Select backup format"), anyList())).thenReturn(0);
        managerFacade.restoreData();
        managerFacade.backupData();
    }

    @Test
    public void testBackupRestoreJson() {
        when(ui.selectOption(eq("Select backup format"), anyList())).thenReturn(1);
        managerFacade.restoreData();
        managerFacade.backupData();
    }

    @Test
    public void testBackupRestoreYaml() {
        when(ui.selectOption(eq("Select backup format"), anyList())).thenReturn(2);
        managerFacade.restoreData();
        managerFacade.backupData();
    }

    public static class MockUI {
        @Bean
        public UserInterface userInterface() {
            return mock(UserInterface.class);
        }
    }

    private static BankAccount bankAccount() {
        return BankAccount.builder()
                .setId(1)
                .setName("Bank")
                .setBalance(0)
                .build();
    }

    @Configuration
    public static class DataSourceConfiguration {
        @Bean
        public DataSource dataSource() {
            System.out.println("Embedded H2 started. Current directory: " + System.getProperty("user.dir"));
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .setName("public")
                    .addScript("create-tables.sql")
                    .build();
        }
    }
}
