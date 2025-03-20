package org.vse.wallet.ui.editor;

import org.vse.wallet.data.Identifiable;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.ui.Editor;
import org.vse.wallet.ui.EditorFactory;
import org.vse.wallet.ui.UserInterface;

public class DataEditorFactory implements EditorFactory {
    private final UserInterface ui;
    private final CategoryRegistry categoryRegistry;
    private final BankAccountRegistry bankAccountRegistry;

    public DataEditorFactory(UserInterface ui,
                             CategoryRegistry categoryRegistry,
                             BankAccountRegistry bankAccountRegistry) {
        this.ui = ui;
        this.categoryRegistry = categoryRegistry;
        this.bankAccountRegistry = bankAccountRegistry;
    }

    @Override
    public <T extends Identifiable> Editor<T> createEditor(EditorType type) {
        var editor = switch (type) {
            case BANK_ACCOUNT -> new BankAccountEditor(ui);
            case CATEGORY -> new CategoryEditor(ui);
            case OPERATION -> new OperationEditor(ui, categoryRegistry.findAll(), bankAccountRegistry.findAll());
        };
        //noinspection unchecked
        return (Editor<T>) editor;
    }
}
