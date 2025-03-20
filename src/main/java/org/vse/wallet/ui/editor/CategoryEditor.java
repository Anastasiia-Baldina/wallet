package org.vse.wallet.ui.editor;

import org.vse.wallet.data.Category;
import org.vse.wallet.data.CategoryType;
import org.vse.wallet.ui.Editor;
import org.vse.wallet.ui.UserInterface;

public class CategoryEditor implements Editor<Category> {
    private final UserInterface ui;

    public CategoryEditor(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public Category create(long id) {
        return Category.builder()
                .setId(id)
                .setName(ui.readString("Name"))
                .setType(selectCategoryType())
                .build();
    }

    @Override
    public Category edit(Category entity) {
        return entity.toBuilder()
                .setName(ui.readString("Name [" + entity.getName() + "]"))
                .setType(selectCategoryType())
                .build();
    }

    @Override
    public Class<Category> getType() {
        return Category.class;
    }

    private CategoryType selectCategoryType() {
        int selected = ui.selectOption("Select type", CategoryType.listNames());
        return CategoryType.values()[selected];
    }
}
