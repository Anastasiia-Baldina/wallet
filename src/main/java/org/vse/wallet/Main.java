package org.vse.wallet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.vse.wallet.di.spring.ApplicationConfiguration;
import org.vse.wallet.di.spring.DataSourceConfiguration;
import org.vse.wallet.di.spring.ServiceConfiguration;
import org.vse.wallet.di.spring.UserInterfaceConfiguration;

public class Main {
    public static void main(String[] args) {
        var diContext = new AnnotationConfigApplicationContext(
                DataSourceConfiguration.class,
                UserInterfaceConfiguration.class,
                ServiceConfiguration.class,
                ApplicationConfiguration.class);
        diContext.getBean(Application.class).start();
        diContext.close();
    }


}
