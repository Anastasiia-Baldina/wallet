package org.vse.wallet.file.json;

import org.vse.wallet.data.Category;
import org.vse.wallet.data.CategoryType;
import org.vse.wallet.utils.Asserts;

import java.util.Map;

public class JsonCategoryLoader extends AbstractJsonLoader<Category> {
    @Override
    protected String getFilename() {
        return "category.json";
    }

    @Override
    protected Category createEntity(Map<String, Object> fields) {
        return Category.builder()
                .setId(Asserts.isLong(fields.get("id"), "id"))
                .setType(CategoryType.valueOf(Asserts.isString(fields.get("type"), "type")))
                .setName(Asserts.isString(fields.get("name"), "name"))
                .build();
    }
}
