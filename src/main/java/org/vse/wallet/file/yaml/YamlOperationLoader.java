package org.vse.wallet.file.yaml;

import org.vse.wallet.data.Operation;
import org.vse.wallet.data.OperationType;
import org.vse.wallet.utils.Asserts;

import java.util.Date;
import java.util.Map;

public class YamlOperationLoader extends AbstractYamlLoader<Operation> {
    @Override
    protected String getFilename() {
        return "operation.yaml";
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
