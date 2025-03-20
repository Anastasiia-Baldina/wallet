package org.vse.wallet.di.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        //dataSource.setUrl("jdbc:h2:./wallet;INIT=create schema if not exists wallet\\; runscript from './create-tables.sql'");
        dataSource.setUrl("jdbc:h2:./wallet");
        dataSource.getConnection().isValid(10_000);
        return dataSource;
    }
}
