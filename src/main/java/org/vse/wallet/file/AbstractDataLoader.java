package org.vse.wallet.file;

import org.vse.wallet.data.Identifiable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class AbstractDataLoader<T extends Identifiable> implements DataLoader<T> {

    public List<T> load() {
        try {
            var lines = Files.readAllLines(Paths.get(getFilename()));
            return parse(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract List<T> parse(List<String> lines);

    protected abstract String getFilename();
}
