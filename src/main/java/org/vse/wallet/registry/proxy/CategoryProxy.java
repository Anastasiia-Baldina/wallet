package org.vse.wallet.registry.proxy;

import org.vse.wallet.data.Category;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.exception.DuplicateEntityException;
import org.vse.wallet.registry.exception.EntityNotExistsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryProxy implements CategoryRegistry {
    private final CategoryRegistry delegate;
    private final Map<Long, Category> cache = new HashMap<>();

    public CategoryProxy(CategoryRegistry delegate) {
        this.delegate = delegate;
        initCache();
    }

    private void initCache() {
        for (Category category : delegate.findAll()) {
            cache.put(category.getId(), category);
        }
    }

    @Override
    public void insert(Category category) {
        if (cache.containsKey(category.getId())) {
            throw new DuplicateEntityException(category);
        } else {
            delegate.insert(category);
            cache.put(category.getId(), category);
        }
    }

    @Override
    public void update(Category category) {
        if (!cache.containsKey(category.getId())) {
            throw new EntityNotExistsException(category);
        }
        delegate.update(category);
        cache.put(category.getId(), category);
    }

    @Override
    @Nonnull
    public List<Category> findAll() {
        return cache.values().stream()
                .sorted(Comparator.comparingLong(Category::getId))
                .toList();
    }

    @Override
    @Nullable
    public Category findById(long id) {
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
