package org.vse.wallet.file;

import org.vse.wallet.data.BankAccount;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.Identifiable;
import org.vse.wallet.data.Operation;
import org.vse.wallet.file.csv.CsvBankAccountLoader;
import org.vse.wallet.file.csv.CsvCategoryLoader;
import org.vse.wallet.file.csv.CsvOperationLoader;
import org.vse.wallet.file.json.JsonBankAccountLoader;
import org.vse.wallet.file.json.JsonCategoryLoader;
import org.vse.wallet.file.json.JsonOperationLoader;
import org.vse.wallet.file.yaml.YamlBankAccountLoader;
import org.vse.wallet.file.yaml.YamlCategoryLoader;
import org.vse.wallet.file.yaml.YamlOperationLoader;

public class DataLoaderFactoryImpl implements DataLoaderFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Identifiable> DataLoader<T> createDataLoader(Format format, Class<T> dataType) {
        if (format == Format.CSV) {
            if (dataType == BankAccount.class) {
                return (DataLoader<T>) new CsvBankAccountLoader();
            } else if (dataType == Operation.class) {
                return (DataLoader<T>) new CsvOperationLoader();
            } else if (dataType == Category.class) {
                return (DataLoader<T>) new CsvCategoryLoader();
            }
            throw new IllegalArgumentException("Unsupported data type: " + dataType);
        } else if (format == Format.JSON) {
            if (dataType == BankAccount.class) {
                return (DataLoader<T>) new JsonBankAccountLoader();
            } else if (dataType == Operation.class) {
                return (DataLoader<T>) new JsonOperationLoader();
            } else if (dataType == Category.class) {
                return (DataLoader<T>) new JsonCategoryLoader();
            }
        } else if (format == Format.YAML) {
            if (dataType == BankAccount.class) {
                return (DataLoader<T>) new YamlBankAccountLoader();
            } else if (dataType == Operation.class) {
                return (DataLoader<T>) new YamlOperationLoader();
            } else if (dataType == Category.class) {
                return (DataLoader<T>) new YamlCategoryLoader();
            }
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
}
