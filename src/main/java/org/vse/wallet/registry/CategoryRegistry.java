package org.vse.wallet.registry;

import org.vse.wallet.data.Category;
import org.vse.wallet.file.DataVisitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface CategoryRegistry extends Registry {

    void insert(Category category);

    void update(Category category);

    @Nonnull
    List<Category> findAll();

    @Nullable
    Category findById(long id);

    void removeAll();

    void removeById(long id);

    @Override
    default void accept(DataVisitor visitor) {
        visitor.visitCategoryRegistry(this);
    }
}
