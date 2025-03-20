package org.vse.wallet.registry.proxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.data.Operation;
import org.vse.wallet.registry.OperationRegistry;
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
public class OperationProxyTest {
    @Mock
    private OperationRegistry delegate;
    @Mock
    private Operation operation1;
    @Mock
    private Operation operation2;
    @InjectMocks
    private OperationProxy operationProxy;

    @Test
    public void testInsert() {
        when(operation1.getId()).thenReturn(1L);
        operationProxy.insert(operation1);
        verify(delegate).insert(operation1);
        assertNotNull(operationProxy.findById(1L));
    }

    @Test
    public void testInsertDuplicate() {
        when(operation1.getId()).thenReturn(1L);
        when(operation2.getId()).thenReturn(2L);
        operationProxy.insert(operation1);
        operationProxy.insert(operation2);
        assertThrows(DuplicateEntityException.class, () -> operationProxy.insert(operation1));
    }

    @Test
    public void testUpdate() {
        String descriprion = UUID.randomUUID().toString();
        when(operation1.getId()).thenReturn(1L);
        operationProxy.insert(operation1);
        when(operation1.getDescription()).thenReturn(descriprion);
        operationProxy.update(operation1);
        assertNotNull(operationProxy.findById(1L));
        assertEquals(descriprion, operationProxy.findById(1L).getDescription());
    }

    @Test
    public void testUpdateNotExists() {
        when(operation1.getId()).thenReturn(1L);
        assertThrows(EntityNotExistsException.class, () -> operationProxy.update(operation1));
    }

    @Test
    public void testFindAll() {
        when(operation1.getId()).thenReturn(1L);
        when(operation2.getId()).thenReturn(2L);
        operationProxy.insert(operation1);
        operationProxy.insert(operation2);
        List<Operation> result = operationProxy.findAll();
        assertEquals(Arrays.asList(operation1, operation2), result);
    }

    @Test
    public void testFindById() {
        when(operation1.getId()).thenReturn(1L);
        operationProxy.insert(operation1);
        Operation result = operationProxy.findById(1L);
        assertEquals(operation1.getId(), result.getId());
    }

    @Test
    public void testRemoveAll() {
        when(operation1.getId()).thenReturn(1L);
        when(operation2.getId()).thenReturn(2L);
        operationProxy.insert(operation1);
        operationProxy.insert(operation2);
        operationProxy.removeAll();
        verify(delegate).removeAll();
        assertTrue(operationProxy.findAll().isEmpty());
    }

    @Test
    public void testRemoveById() {
        when(operation1.getId()).thenReturn(1L);
        when(operation2.getId()).thenReturn(2L);
        operationProxy.insert(operation1);
        operationProxy.insert(operation2);
        operationProxy.removeById(1L);
        verify(delegate).removeById(1L);
        assertNull(operationProxy.findById(1L));
    }
}