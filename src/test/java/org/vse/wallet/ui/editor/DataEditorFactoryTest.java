package org.vse.wallet.ui.editor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.data.BankAccount;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.Operation;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.ui.Editor;
import org.vse.wallet.ui.UserInterface;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataEditorFactoryTest {
    @Mock
    private UserInterface ui;
    @Mock
    private CategoryRegistry categoryRegistry;
    @Mock
    private BankAccountRegistry bankAccountRegistry;
    @Mock
    private List<Category> categories;
    @Mock
    private List<BankAccount> bankAccounts;
    @InjectMocks
    private DataEditorFactory dataEditorFactory;

    @Test
    public void testCreateEditorBankAccount() {
        Editor<BankAccount> editor = dataEditorFactory.createEditor(EditorType.BANK_ACCOUNT);
        assertTrue(editor instanceof BankAccountEditor);
    }

    @Test
    public void testCreateEditorCategory() {
        Editor<Category> editor = dataEditorFactory.createEditor(EditorType.CATEGORY);
        assertTrue(editor instanceof CategoryEditor);
    }

    @Test
    public void testCreateEditorOperation() {
        when(categoryRegistry.findAll()).thenReturn(categories);
        when(bankAccountRegistry.findAll()).thenReturn(bankAccounts);
        Editor<Operation> editor = dataEditorFactory.createEditor(EditorType.OPERATION);
        assertTrue(editor instanceof OperationEditor);
    }
}