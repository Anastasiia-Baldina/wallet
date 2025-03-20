package org.vse.wallet.ui.editor;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.CategoryType;
import org.vse.wallet.ui.UserInterface;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryEditorTest {
    @Mock
    private UserInterface ui;
    @InjectMocks
    private CategoryEditor categoryEditor;

    @Test
    public void testCreate() {
        long id = RandomUtils.nextLong();
        String name = UUID.randomUUID().toString();
        when(ui.readString("Name")).thenReturn(name);
        when(ui.selectOption(eq("Select type"), anyList())).thenReturn(0);
        Category expected = Category.builder()
                .setId(id)
                .setName(name)
                .setType(CategoryType.EXPENSE)
                .build();
        Category actual = categoryEditor.create(id);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testEdit() {
        long id = RandomUtils.nextLong();
        String name = UUID.randomUUID().toString();
        Category entity = Category.builder()
                .setId(id)
                .setName("Old name")
                .setType(CategoryType.EXPENSE)
                .build();
        when(ui.readString("Name [" + entity.getName() + "]")).thenReturn(name);
        when(ui.selectOption(eq("Select type"), anyList())).thenReturn(1);
        Category expected = entity.toBuilder()
                .setName(name)
                .setType(CategoryType.INCOME)
                .build();
        Category actual = categoryEditor.edit(entity);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testGetType() {
        assertEquals(Category.class, categoryEditor.getType());
    }
}