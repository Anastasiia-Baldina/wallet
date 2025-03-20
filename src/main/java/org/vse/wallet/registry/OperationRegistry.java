package org.vse.wallet.registry;

import org.vse.wallet.data.Operation;
import org.vse.wallet.file.DataVisitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface OperationRegistry extends Registry {

    void insert(Operation operation);

    void update(Operation operation);

    void removeAll();

    @Nullable
    Operation findById(long id);

    @Nonnull
    List<Operation> findByBankAccountId(long bankAccountId);

    @Nonnull
    List<Operation> findByCategoryId(long categoryId);

    @Nonnull
    List<Operation> findAll();

    void removeById(long id);

    void removeByBankAccountId(long bankAccountId);

    default void accept(DataVisitor visitor) {
        visitor.visitOperationRegistry(this);
    }
}
