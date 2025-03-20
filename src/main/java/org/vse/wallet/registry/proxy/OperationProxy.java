package org.vse.wallet.registry.proxy;

import org.vse.wallet.data.Operation;
import org.vse.wallet.registry.OperationRegistry;
import org.vse.wallet.registry.exception.DuplicateEntityException;
import org.vse.wallet.registry.exception.EntityNotExistsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationProxy implements OperationRegistry {
    private final OperationRegistry delegate;
    private final Map<Long, Operation> cache = new HashMap<>();

    public OperationProxy(OperationRegistry delegate) {
        this.delegate = delegate;
        initCache();
    }

    private void initCache() {
        for (Operation operation : delegate.findAll()) {
            cache.put(operation.getId(), operation);
        }
    }

    @Override
    public void insert(Operation operation) {
        if (cache.containsKey(operation.getId())) {
            throw new DuplicateEntityException(operation);
        } else {
            delegate.insert(operation);
            cache.put(operation.getId(), operation);
        }
    }

    @Override
    public void update(Operation operation) {
        if(!cache.containsKey(operation.getId())) {
            throw new EntityNotExistsException(operation);
        }
        delegate.update(operation);
        cache.put(operation.getId(), operation);
    }

    @Override
    public void removeAll() {
        delegate.removeAll();
        cache.clear();
    }

    @Nullable
    @Override
    public Operation findById(long id) {
        return cache.get(id);
    }

    @Nonnull
    @Override
    public List<Operation> findByBankAccountId(long bankAccountId) {
        return cache.values().stream()
                .filter(operation -> operation.getBankAccountId() == bankAccountId)
                .sorted(Comparator.comparingLong(operation -> operation.getDate().getTime()))
                .toList();
    }

    @Nonnull
    @Override
    public List<Operation> findByCategoryId(long categoryId) {
        return cache.values().stream()
                .filter(operation -> operation.getCategoryId() == categoryId)
                .sorted(Comparator.comparingLong(operation -> operation.getDate().getTime()))
                .toList();
    }

    @Nonnull
    @Override
    public List<Operation> findAll() {
        return cache.values().stream()
                .sorted(Comparator.comparingLong(Operation::getId))
                .toList();
    }

    @Override
    public void removeById(long id) {
        if(cache.containsKey(id)) {
            delegate.removeById(id);
            cache.remove(id);
        }
    }

    @Override
    public void removeByBankAccountId(long bankAccountId) {
        delegate.removeByBankAccountId(bankAccountId);
        for(var account : findByBankAccountId(bankAccountId)) {
            cache.remove(account.getId());
        }
    }
}
