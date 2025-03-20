package org.vse.wallet.file.yaml;

import org.vse.wallet.data.Category;
import org.vse.wallet.data.CategoryType;
import org.vse.wallet.utils.Asserts;

import java.util.Map;

public class YamlCategoryLoader extends AbstractYamlLoader<Category> {
    @Override
    protected String getFilename() {
        return "category.yaml";
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
