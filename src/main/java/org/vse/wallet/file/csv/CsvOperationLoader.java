package org.vse.wallet.file.csv;

import org.vse.wallet.data.Operation;
import org.vse.wallet.data.OperationType;
import org.vse.wallet.file.exception.ParserException;

import javax.annotation.Nullable;
import java.util.Date;

public class CsvOperationLoader extends AbstractCsvLoader<Operation> {

    @Override
    protected String getFilename() {
        return "operation.csv";
    }

    @Override
    protected Operation createEntity(String[] fieldValues) {
        if (fieldValues.length != 7) {
            throw new ParserException("Expected 7 values but found " + fieldValues.length);
        }
        return Operation.builder()
                .setId(Long.parseLong(fieldValues[0]))
                .setType(OperationType.valueOf(fieldValues[1]))
                .setBankAccountId(Long.parseLong(fieldValues[2]))
                .setAmount(Long.parseLong(fieldValues[3]))
                .setDate(new Date(Long.parseLong(fieldValues[4])))
                .setDescription(parseNullableString(fieldValues[5]))
                .setCategoryId(Long.parseLong(fieldValues[6]))
                .build();
    }

    @Nullable
    private static String parseNullableString(String value) {
        return "null".equals(value) ? null : value;
    }
}
