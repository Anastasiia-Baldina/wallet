package org.vse.wallet.facade.impl;

import org.vse.wallet.data.Category;
import org.vse.wallet.facade.CategoryFacade;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.OperationRegistry;
import org.vse.wallet.ui.Editor;
import org.vse.wallet.ui.EditorFactory;
import org.vse.wallet.ui.UserInterface;
import org.vse.wallet.ui.editor.EditorType;
import org.vse.wallet.utils.Ids;

import javax.annotation.Nullable;

public class CategoryFacadeImpl implements CategoryFacade {
    private final CategoryRegistry categoryRegistry;
    private final OperationRegistry operationRegistry;
    private final UserInterface ui;
    private final EditorFactory editorFactory;

    public CategoryFacadeImpl(CategoryRegistry categoryRegistry, OperationRegistry operationRegistry, UserInterface ui, EditorFactory editorFactory) {
        this.categoryRegistry = categoryRegistry;
        this.operationRegistry = operationRegistry;
        this.ui = ui;
        this.editorFactory = editorFactory;
    }

    @Override
    public void listCategories() {
        var data = categoryRegistry.findAll();
        if (data.isEmpty()) {
            ui.printLine("\tNo categories found");
        } else {
            ui.printLine("Categories:");
            data.forEach(entity -> {
                ui.printLine(entity.toString());
            });
        }
    }

    @Override
    public void createCategory() {
        Editor<Category> editor = editorFactory.createEditor(EditorType.CATEGORY);
        Category newEntity = editor.create(Ids.generateId());
        categoryRegistry.insert(newEntity);
    }

    @Override
    public void editCategory() {
        var entity = selectEntity();
        if (entity != null) {
            Editor<Category> editor = editorFactory.createEditor(EditorType.CATEGORY);
            var newEntity = editor.edit(entity);
            categoryRegistry.update(newEntity);
        }
    }

    @Override
    public void deleteCategory() {
        var entity = selectEntity();
        if (entity != null) {
            if (!operationRegistry.findByCategoryId(entity.getId()).isEmpty()) {
                ui.printLine("\tCan't delete category with operations");
            } else {
                categoryRegistry.removeById(entity.getId());
            }
        }
    }

    @Nullable
    private Category selectEntity() {
        var entities = categoryRegistry.findAll();
        if (entities.isEmpty()) {
            ui.printLine("\tNo categories found");
            return null;
        }
        var entityNames = entities.stream()
                .map(x -> "\t" + x.getId() + " " + x.getName())
                .toList();
        int selected = ui.selectOption("Select category", entityNames);
        return entities.get(selected);
    }
}
