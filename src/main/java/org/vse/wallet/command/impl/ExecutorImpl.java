package org.vse.wallet.command.impl;

import org.vse.wallet.command.Command;
import org.vse.wallet.command.Executor;

import javax.annotation.Nonnull;

public class ExecutorImpl implements Executor {
    @Override
    public void execute(@Nonnull Command command) {
        command.execute();
    }
}
