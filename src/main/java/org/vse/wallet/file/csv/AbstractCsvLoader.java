package org.vse.wallet.file.csv;

import org.vse.wallet.data.Identifiable;
import org.vse.wallet.file.AbstractDataLoader;
import org.vse.wallet.file.exception.ParserException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCsvLoader<T extends Identifiable> extends AbstractDataLoader<T> {
    private static final String DELIMITER = ";";

    @Override
    protected List<T> parse(List<String> lines) {
        List<T> data = new ArrayList<>();
        for (var line : lines) {
            var fieldValues = line.trim().split(DELIMITER);
            try {
                data.add(createEntity(fieldValues));
            } catch (Exception e) {
                throw new ParserException("Failed parse line [" + line + "]", e);
            }
        }
        return data;
    }

    protected abstract T createEntity(String[] fieldValues);
}
