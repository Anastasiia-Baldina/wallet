package org.vse.wallet.file.csv;

import org.vse.wallet.data.Category;
import org.vse.wallet.data.CategoryType;
import org.vse.wallet.file.exception.ParserException;

public class CsvCategoryLoader extends AbstractCsvLoader<Category> {

    @Override
    protected String getFilename() {
        return "category.csv";
    }

    @Override
    protected Category createEntity(String[] fieldValues) {
        if (fieldValues.length != 3) {
            throw new ParserException("Expected 3 values but found " + fieldValues.length);
        }
        return Category.builder()
                .setId(Long.parseLong(fieldValues[0]))
                .setType(CategoryType.valueOf(fieldValues[1]))
                .setName(fieldValues[2])
                .build();
    }
}
