package org.vse.wallet.ui.editor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.data.BankAccount;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.CategoryType;
import org.vse.wallet.data.Operation;
import org.vse.wallet.data.OperationType;
import org.vse.wallet.ui.UserInterface;
import org.vse.wallet.utils.Dates;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationEditorTest {
    @Mock
    private UserInterface ui;

    @Test
    public void testCreate() {
        long id = 1L;
        long bankAccountId = 2L;
        OperationType type = OperationType.INCOME;
        long categoryId = 3L;
        Date date = new Date();
        String description = "Test";
        int amount = 100;
        BankAccount bankAccount = BankAccount.builder()
                .setId(bankAccountId)
                .setName("Test")
                .build();
        List<BankAccount> bankAccounts = List.of(bankAccount);
        Category category = Category.builder()
                .setId(categoryId)
                .setName("Test")
                .setType(CategoryType.INCOME)
                .build();
        List<Category> categories = List.of(category);
        OperationEditor operationEditor = new OperationEditor(ui, categories, bankAccounts);
        when(ui.readDate("Date")).thenReturn(date);
        when(ui.readString("Description")).thenReturn(description);
        when(ui.readInt("Amount")).thenReturn(amount);
        when(ui.selectOption(eq("Select type"), anyList())).thenReturn(type.ordinal());
        when(ui.selectOption(eq("Select category"), anyList())).thenReturn(0);
        when(ui.selectOption(eq("Select bank account"), anyList())).thenReturn(0);
        Operation expected = Operation.builder()
                .setId(id)
                .setBankAccountId(bankAccountId)
                .setType(type)
                .setCategoryId(categoryId)
                .setDate(date)
                .setDescription(description)
                .setAmount(amount)
                .build();

        Operation actual = operationEditor.create(id);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testEdit() {
        long id = 1L;
        long bankAccountId = 2L;
        OperationType type = OperationType.INCOME;
        long categoryId = 3L;
        Date date = new Date();
        String description = "Test";
        int amount = 100;
        Operation op = Operation.builder()
                .setId(id)
                .setBankAccountId(0)
                .setType(OperationType.EXPENSE)
                .setCategoryId(0)
                .setDate(new Date())
                .setDescription("Old description")
                .setAmount(0)
                .build();
        BankAccount bankAccount = BankAccount.builder()
                .setId(bankAccountId)
                .setName("Test")
                .build();
        List<BankAccount> bankAccounts = List.of(bankAccount);
        Category category = Category.builder()
                .setId(categoryId)
                .setName("Test")
                .setType(CategoryType.INCOME)
                .build();
        List<Category> categories = List.of(category);
        OperationEditor operationEditor = new OperationEditor(ui, categories, bankAccounts);
        when(ui.readDate("Date [" + Dates.format(op.getDate()) + "]")).thenReturn(date);
        when(ui.readString("Description [" + op.getDescription() + "]")).thenReturn(description);
        when(ui.readInt("Amount [" + op.getAmount() + "]")).thenReturn(amount);
        when(ui.selectOption(eq("Select type"), anyList())).thenReturn(type.ordinal());
        when(ui.selectOption(eq("Select category"), anyList())).thenReturn(0);
        when(ui.selectOption(eq("Select bank account"), anyList())).thenReturn(0);
        Operation expected = op.toBuilder()
                .setType(type)
                .setBankAccountId(bankAccountId)
                .setCategoryId(categoryId)
                .setDate(date)
                .setDescription(description)
                .setAmount(amount)
                .build();

        Operation actual = operationEditor.edit(op);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testGetType() {
        OperationEditor operationEditor = new OperationEditor(ui, List.of(mock(Category.class)), List.of(mock(BankAccount.class)));
        assertEquals(Operation.class, operationEditor.getType());
    }
}