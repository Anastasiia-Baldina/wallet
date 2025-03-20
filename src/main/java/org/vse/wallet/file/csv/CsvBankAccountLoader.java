package org.vse.wallet.file.csv;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.file.exception.ParserException;

public class CsvBankAccountLoader extends AbstractCsvLoader<BankAccount> {

    @Override
    protected String getFilename() {
        return "bank-account.csv";
    }

    @Override
    protected BankAccount createEntity(String[] fieldValues) {
        if (fieldValues.length != 3) {
            throw new ParserException("Expected 3 values but found " + fieldValues.length);
        }
        return BankAccount.builder()
                .setId(Long.parseLong(fieldValues[0]))
                .setName(fieldValues[1])
                .setBalance(Long.parseLong(fieldValues[2]))
                .build();
    }
}
