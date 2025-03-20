package org.vse.wallet.ui.editor;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.data.BankAccount;
import org.vse.wallet.ui.UserInterface;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountEditorTest {
    @Mock
    private UserInterface ui;
    @InjectMocks
    private BankAccountEditor bankAccountEditor;

    @Test
    public void testCreate() {
        long id = RandomUtils.nextLong();
        String name = UUID.randomUUID().toString();
        when(ui.readString("Name")).thenReturn(name);
        BankAccount expected = BankAccount.builder()
                .setId(id)
                .setName(name)
                .build();
        BankAccount actual = bankAccountEditor.create(id);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testEdit() {
        long id = RandomUtils.nextLong();
        String name = UUID.randomUUID().toString();
        BankAccount entity = BankAccount.builder()
                .setId(id)
                .setName("Old name")
                .setBalance(RandomUtils.nextLong())
                .build();
        when(ui.readString("Name [" + entity.getName() + "]")).thenReturn(name);
        BankAccount expected = entity.toBuilder()
                .setName(name)
                .build();
        BankAccount actual = bankAccountEditor.edit(entity);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testGetType() {
        assertEquals(BankAccount.class, bankAccountEditor.getType());
    }
}