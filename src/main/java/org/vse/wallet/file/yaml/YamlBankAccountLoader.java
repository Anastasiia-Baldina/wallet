package org.vse.wallet.file.yaml;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.utils.Asserts;

import java.util.Map;

public class YamlBankAccountLoader extends AbstractYamlLoader<BankAccount> {
    @Override
    protected String getFilename() {
        return "bank-account.yaml";
    }

    @Override
    protected BankAccount createEntity(Map<String, Object> fields) {
        return BankAccount.builder()
                .setId(Asserts.isLong(fields.get("id"), "id"))
                .setName(Asserts.isString(fields.get("name"), "name"))
                .setBalance(Asserts.isLong(fields.get("balance"), "balance"))
                .build();
    }
}
