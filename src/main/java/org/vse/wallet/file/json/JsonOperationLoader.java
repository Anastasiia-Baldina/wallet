package org.vse.wallet.file.json;

import org.vse.wallet.data.Operation;
import org.vse.wallet.data.OperationType;
import org.vse.wallet.utils.Asserts;

import java.util.Date;
import java.util.Map;

public class JsonOperationLoader extends AbstractJsonLoader<Operation> {
    @Override
    protected String getFilename() {
        return "operation.json";
    }

    @Override
    protected Operation createEntity(Map<String, Object> fields) {
        return Operation.builder()
                .setId(Asserts.isLong(fields.get("id"), "id"))
                .setType(OperationType.valueOf(Asserts.isString(fields.get("type"), "type")))
                .setCategoryId(Asserts.isLong(fields.get("categoryId"), "categoryId"))
                .setDescription(Asserts.isString(fields.get("description"), "description"))
                .setAmount(Asserts.isLong(fields.get("amount"), "amount"))
                .setBankAccountId(Asserts.isLong(fields.get("bankAccountId"), "bankAccountId"))
                .setDate(new Date(Asserts.isLong(fields.get("date"), "date")))
                .build();
    }
}
