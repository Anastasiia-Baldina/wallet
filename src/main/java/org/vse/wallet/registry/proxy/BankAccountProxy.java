package org.vse.wallet.registry.proxy;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.exception.DuplicateEntityException;
import org.vse.wallet.registry.exception.EntityNotExistsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountProxy implements BankAccountRegistry {
    private final BankAccountRegistry delegate;
    private final Map<Long, BankAccount> cache = new HashMap<>();

    public BankAccountProxy(BankAccountRegistry delegate) {
        this.delegate = delegate;
        initCache();
    }

    private void initCache() {
        for (var bankAccount : delegate.findAll()) {
            cache.put(bankAccount.getId(), bankAccount);
        }
    }

    @Override
    public void insert(BankAccount bankAccount) {
        if (cache.containsKey(bankAccount.getId())) {
            throw new DuplicateEntityException(bankAccount);
        } else {
            delegate.insert(bankAccount);
            cache.put(bankAccount.getId(), bankAccount);
        }
    }

    @Override
    public void update(BankAccount bankAccount) {
        if (!cache.containsKey(bankAccount.getId())) {
            throw new EntityNotExistsException(bankAccount);
        }
        delegate.update(bankAccount);
        cache.put(bankAccount.getId(), bankAccount);
    }

    @Nonnull
    @Override
    public List<BankAccount> findAll() {
        return cache.values().stream()
                .sorted(Comparator.comparingLong(BankAccount::getId))
                .toList();
    }

    @Nullable
    @Override
    public BankAccount findById(Long id) {
        return cache.get(id);
    }

    @Override
    public void removeAll() {
        if (!cache.isEmpty()) {
            delegate.removeAll();
            cache.clear();
        }
    }

    @Override
    public void removeById(long id) {
        if (cache.containsKey(id)) {
            delegate.removeById(id);
            cache.remove(id);
        }
    }
}
