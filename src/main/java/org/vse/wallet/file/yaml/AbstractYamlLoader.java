package org.vse.wallet.file.yaml;

import org.vse.wallet.data.Identifiable;
import org.vse.wallet.file.AbstractDataLoader;
import org.vse.wallet.file.exception.ParserException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractYamlLoader<T extends Identifiable> extends AbstractDataLoader<T> {

    @Override
    protected List<T> parse(List<String> lines) {
        List<T> data = new ArrayList<>();
        Map<String, Object> fields = new HashMap<>();
        for(String line : lines) {
            if (line.startsWith("- ")) {
                if (!fields.isEmpty()) {
                    data.add(createEntity(fields));
                    fields.clear();
                }
            }
            var field = parseField(line.substring(2));
            fields.put(field.name, field.value);
        }
        if (!fields.isEmpty()) {
            data.add(createEntity(fields));
        }
        return data;
    }

    @Nonnull
    private static Field parseField(String line) {
        var parts = line.trim().split(":");
        if(parts.length != 2) {
            throw new ParserException("Expected 2 parts separated with ':'");
        }
        var fieldName = parts[0].trim();
        var value = parts[1].trim();
        if(value.startsWith("\"") && value.endsWith("\"")) {
            var strVal = value.substring(1, value.length() - 1);
            return new Field(fieldName, strVal);
        }
        return new Field(fieldName, Long.parseLong(value));
    }

    protected abstract T createEntity(Map<String, Object> fields);

    private record Field(String name, Object value) {}
}
