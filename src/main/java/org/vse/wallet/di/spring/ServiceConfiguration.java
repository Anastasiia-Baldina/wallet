package org.vse.wallet.di.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.vse.wallet.facade.BankAccountFacade;
import org.vse.wallet.facade.ManagerFacade;
import org.vse.wallet.facade.OperationFacade;
import org.vse.wallet.facade.impl.BankAccountFacadeImpl;
import org.vse.wallet.facade.CategoryFacade;
import org.vse.wallet.facade.impl.CategoryFacadeImpl;
import org.vse.wallet.facade.impl.ManagerFacadeImpl;
import org.vse.wallet.facade.impl.OperationFacadeImpl;
import org.vse.wallet.file.DataLoaderFactory;
import org.vse.wallet.file.DataLoaderFactoryImpl;
import org.vse.wallet.file.DataSaver;
import org.vse.wallet.file.StorageVisitorFactory;
import org.vse.wallet.file.StorageVisitorFactoryImpl;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.OperationRegistry;
import org.vse.wallet.registry.h2.DbBankAccountRegistry;
import org.vse.wallet.registry.h2.DbCategoryRegistry;
import org.vse.wallet.registry.h2.DbOperationRegistry;
import org.vse.wallet.registry.proxy.BankAccountProxy;
import org.vse.wallet.registry.proxy.CategoryProxy;
import org.vse.wallet.registry.proxy.OperationProxy;
import org.vse.wallet.ui.EditorFactory;
import org.vse.wallet.ui.UserInterface;
import org.vse.wallet.ui.editor.DataEditorFactory;

import javax.sql.DataSource;

@Configuration
public class ServiceConfiguration {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserInterface ui;

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    BankAccountRegistry bankAccountRegistry() {
        return new BankAccountProxy(new DbBankAccountRegistry(namedParameterJdbcTemplate()));
    }

    @Bean
    CategoryRegistry categoryRegistry() {
        return new CategoryProxy(new DbCategoryRegistry(namedParameterJdbcTemplate()));
    }

    @Bean
    OperationRegistry operationRegistry() {
        return new OperationProxy(new DbOperationRegistry(namedParameterJdbcTemplate()));
    }

    @Bean
    EditorFactory editorFactory() {
        return new DataEditorFactory(ui, categoryRegistry(), bankAccountRegistry());
    }

    @Bean
    public BankAccountFacade bankAccountFacade() {
        return new BankAccountFacadeImpl(bankAccountRegistry(), editorFactory(), ui);
    }

    @Bean
    public CategoryFacade categoryFacade() {
        return new CategoryFacadeImpl(categoryRegistry(), operationRegistry(), ui, editorFactory());
    }

    @Bean
    public OperationFacade operationFacade() {
        return new OperationFacadeImpl(
                editorFactory(),
                operationRegistry(),
                bankAccountRegistry(),
                categoryRegistry(),
                ui);
    }

    @Bean
    StorageVisitorFactory storageVisitorFactory() {
        return new StorageVisitorFactoryImpl();
    }

    @Bean
    DataSaver dataSaver() {
        return new DataSaver(storageVisitorFactory());
    }

    @Bean
    DataLoaderFactory dataLoaderFactory() {
        return new DataLoaderFactoryImpl();
    }

    @Bean
    public ManagerFacade managerFacade() {
        return new ManagerFacadeImpl(
                bankAccountRegistry(),
                categoryRegistry(),
                operationRegistry(),
                ui,
                dataSaver(),
                dataLoaderFactory());
    }
}
