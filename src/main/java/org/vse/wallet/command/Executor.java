package org.vse.wallet.command;

import javax.annotation.Nonnull;

public interface Executor {
    void execute(@Nonnull Command command);
}
