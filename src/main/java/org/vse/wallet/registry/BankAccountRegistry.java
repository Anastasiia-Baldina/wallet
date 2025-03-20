package org.vse.wallet.registry;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.file.DataVisitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface BankAccountRegistry extends Registry<BankAccount> {

    void insert(BankAccount bankAccount);

    void update(BankAccount bankAccount);

    @Nonnull
    List<BankAccount> findAll();

    @Nullable
    BankAccount findById(Long id);

    void removeAll();

    void removeById(long id);

    @Override
    default void accept(DataVisitor visitor) {
        visitor.visitBankAccountRegistry(this);
    }
}
