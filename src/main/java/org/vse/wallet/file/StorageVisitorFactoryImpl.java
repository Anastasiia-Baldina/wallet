package org.vse.wallet.file;

import org.vse.wallet.file.csv.CsvSaverVisitor;
import org.vse.wallet.file.json.JsonSaverVisitor;
import org.vse.wallet.file.yaml.YamlSaverVisitor;

public class StorageVisitorFactoryImpl implements StorageVisitorFactory {

    @Override
    public DataVisitor get(Format format) {
        return switch (format) {
            case CSV -> new CsvSaverVisitor();
            case JSON -> new JsonSaverVisitor();
            case YAML -> new YamlSaverVisitor();
        };
    }
}
