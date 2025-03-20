package org.vse.wallet.registry.proxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.data.Category;
import org.vse.wallet.registry.CategoryRegistry;
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
public class CategoryProxyTest {
    @Mock
    private CategoryRegistry delegate;
    @Mock
    private Category category1;
    @Mock
    private Category category2;
    @InjectMocks
    private CategoryProxy categoryProxy;

    @Test
    public void testInsert() {
        when(category1.getId()).thenReturn(1L);
        categoryProxy.insert(category1);
        verify(delegate).insert(category1);
        assertNotNull(categoryProxy.findById(1L));
    }

    @Test
    public void testInsertDuplicate() {
        when(category1.getId()).thenReturn(1L);
        when(category2.getId()).thenReturn(2L);
        categoryProxy.insert(category1);
        categoryProxy.insert(category2);
        assertThrows(DuplicateEntityException.class, () -> categoryProxy.insert(category1));
    }

    @Test
    public void testUpdate() {
        String name = UUID.randomUUID().toString();
        when(category1.getId()).thenReturn(1L);
        categoryProxy.insert(category1);
        when(category1.getName()).thenReturn(name);
        categoryProxy.update(category1);
        assertNotNull(categoryProxy.findById(1L));
        assertEquals(name, categoryProxy.findById(1L).getName());
    }

    @Test
    public void testUpdateNotExists() {
        when(category1.getId()).thenReturn(1L);
        assertThrows(EntityNotExistsException.class, () -> categoryProxy.update(category1));
    }

    @Test
    public void testFindAll() {
        when(category1.getId()).thenReturn(1L);
        when(category2.getId()).thenReturn(2L);
        categoryProxy.insert(category1);
        categoryProxy.insert(category2);
        List<Category> result = categoryProxy.findAll();
        assertEquals(Arrays.asList(category1, category2), result);
    }

    @Test
    public void testFindById() {
        when(category1.getId()).thenReturn(1L);
        categoryProxy.insert(category1);
        Category result = categoryProxy.findById(1L);
        assertEquals(category1.getId(), result.getId());
    }

    @Test
    public void testRemoveAll() {
        when(category1.getId()).thenReturn(1L);
        when(category2.getId()).thenReturn(2L);
        categoryProxy.insert(category1);
        categoryProxy.insert(category2);
        categoryProxy.removeAll();
        verify(delegate).removeAll();
        assertTrue(categoryProxy.findAll().isEmpty());
    }

    @Test
    public void testRemoveById() {
        when(category1.getId()).thenReturn(1L);
        when(category2.getId()).thenReturn(2L);
        categoryProxy.insert(category1);
        categoryProxy.insert(category2);
        categoryProxy.removeById(1L);
        verify(delegate).removeById(1L);
        assertNull(categoryProxy.findById(1L));
    }
}