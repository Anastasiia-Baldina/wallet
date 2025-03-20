package org.vse.wallet.file.json;

import org.vse.wallet.data.Identifiable;
import org.vse.wallet.file.AbstractDataLoader;
import org.vse.wallet.file.exception.ParserException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractJsonLoader<T extends Identifiable> extends AbstractDataLoader<T> {
    @Override
    protected List<T> parse(List<String> lines) {
        List<T> data = new ArrayList<>();
        Map<String, Object> fields = new HashMap<>();
        for (var line : lines) {
            line = line.trim();
            try {
                if ("[".equals(line)) {
                    continue;
                }
                if ("]".equals(line)) {
                    break;
                } else if ("{".equals(line)) {
                    fields.clear();
                } else if (line.startsWith("}")) {
                    data.add(createEntity(fields));
                } else {
                    var field = parseField(line);
                    fields.put(field.name(), field.value());
                }
            } catch (Exception e) {
                throw new ParserException("Failed parse line [" + line + "]", e);
            }
        }
        return data;
    }

    @Nonnull
    private static Field parseField(String line) {
        var parts = line.split(":");
        if (parts.length != 2) {
            throw new ParserException("Expected 2 parts separated with ':'");
        }
        var fieldName = unwrapFieldName(parts[0].trim());
        var value = parts[1].trim();
        if(value.endsWith(",")) {
            value = value.substring(0, value.length() - 1);
        }
        if (value.startsWith("\"") && value.endsWith("\"")) {
            var strVal = value.substring(1, value.length() - 1);
            return new Field(fieldName, strVal);
        }
        return new Field(fieldName, Long.parseLong(value));
    }

    private static String unwrapFieldName(String fieldName) {
        if (fieldName.startsWith("\"") && fieldName.endsWith("\"")) {
            return fieldName.substring(1, fieldName.length() - 1);
        } else {
            return fieldName;
        }
    }

    protected abstract T createEntity(Map<String, Object> fields);

    private record Field(String name, Object value) {
    }
}
