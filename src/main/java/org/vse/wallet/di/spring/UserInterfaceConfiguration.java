package org.vse.wallet.di.spring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vse.wallet.ui.ConsoleUserInterface;
import org.vse.wallet.ui.UserInterface;

@Configuration
public class UserInterfaceConfiguration {
    @Bean
    public UserInterface userInterface() {
        return new ConsoleUserInterface();
    }
}
