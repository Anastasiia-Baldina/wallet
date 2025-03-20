package org.vse.wallet.di.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vse.wallet.Application;
import org.vse.wallet.command.CommandFactory;
import org.vse.wallet.command.Executor;
import org.vse.wallet.command.impl.CommandFactoryImpl;
import org.vse.wallet.command.impl.ExecutorImpl;
import org.vse.wallet.facade.BankAccountFacade;
import org.vse.wallet.facade.CategoryFacade;
import org.vse.wallet.facade.ManagerFacade;
import org.vse.wallet.facade.OperationFacade;
import org.vse.wallet.ui.UserInterface;

@Configuration
public class ApplicationConfiguration {
    @Autowired
    private BankAccountFacade bankAccountFacade;
    @Autowired
    private CategoryFacade categoryFacade;
    @Autowired
    private OperationFacade operationFacade;
    @Autowired
    private ManagerFacade managerFacade;
    @Autowired
    private UserInterface ui;

    @Bean
    CommandFactory commandFactory() {
        return new CommandFactoryImpl(bankAccountFacade, categoryFacade, operationFacade, managerFacade);
    }

    @Bean
    Executor executor() {
        return new ExecutorImpl();
    }

    @Bean
    Application application() {
        return new Application(ui, executor(), commandFactory());
    }
}
