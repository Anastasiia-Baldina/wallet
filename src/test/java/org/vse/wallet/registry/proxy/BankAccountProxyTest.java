package org.vse.wallet.registry.proxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.data.BankAccount;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.exception.DuplicateEntityException;
import org.vse.wallet.registry.exception.EntityNotExistsException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountProxyTest {
    @Mock
    private BankAccountRegistry delegate;
    @Mock
    private BankAccount bankAccount1;
    @Mock
    private BankAccount bankAccount2;
    @InjectMocks
    private BankAccountProxy bankAccountProxy;

    @Test
    public void testInsert() {
        when(bankAccount1.getId()).thenReturn(1L);
        bankAccountProxy.insert(bankAccount1);
        verify(delegate).insert(bankAccount1);
        assertNotNull(bankAccountProxy.findById(1L));
    }

    @Test
    public void testInsertDuplicate() {
        when(bankAccount1.getId()).thenReturn(1L);
        when(bankAccount2.getId()).thenReturn(2L);
        bankAccountProxy.insert(bankAccount1);
        bankAccountProxy.insert(bankAccount2);
        assertThrows(DuplicateEntityException.class, () -> bankAccountProxy.insert(bankAccount1));
    }

    @Test
    public void testUpdate() {
        String name = UUID.randomUUID().toString();
        when(bankAccount1.getId()).thenReturn(1L);
        bankAccountProxy.insert(bankAccount1);
        when(bankAccount1.getName()).thenReturn(name);
        bankAccountProxy.update(bankAccount1);
        assertNotNull(bankAccountProxy.findById(1L));
        assertEquals(name, bankAccountProxy.findById(1L).getName());
    }

    @Test
    public void testUpdateNotExists() {
        when(bankAccount1.getId()).thenReturn(1L);
        assertThrows(EntityNotExistsException.class, () -> bankAccountProxy.update(bankAccount1));
    }

    @Test
    public void testFindAll() {
        when(bankAccount1.getId()).thenReturn(1L);
        when(bankAccount2.getId()).thenReturn(2L);
        bankAccountProxy.insert(bankAccount1);
        bankAccountProxy.insert(bankAccount2);
        List<BankAccount> result = bankAccountProxy.findAll();
        assertEquals(Arrays.asList(bankAccount1, bankAccount2), result);
    }

    @Test
    public void testFindById() {
        when(bankAccount1.getId()).thenReturn(1L);
        bankAccountProxy.insert(bankAccount1);
        BankAccount result = bankAccountProxy.findById(1L);
        assertEquals(bankAccount1.getId(), result.getId());
    }

    @Test
    public void testRemoveAll() {
        when(bankAccount1.getId()).thenReturn(1L);
        when(bankAccount2.getId()).thenReturn(2L);
        bankAccountProxy.insert(bankAccount1);
        bankAccountProxy.insert(bankAccount2);
        bankAccountProxy.removeAll();
        verify(delegate).removeAll();
        assertTrue(bankAccountProxy.findAll().isEmpty());
    }

    @Test
    public void testRemoveById() {
        when(bankAccount1.getId()).thenReturn(1L);
        when(bankAccount2.getId()).thenReturn(2L);
        bankAccountProxy.insert(bankAccount1);
        bankAccountProxy.insert(bankAccount2);
        bankAccountProxy.removeById(1L);
        verify(delegate).removeById(1L);
        assertNull(bankAccountProxy.findById(1L));
    }
}