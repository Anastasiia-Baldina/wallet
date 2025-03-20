package org.vse.wallet.file;

import org.vse.wallet.data.Identifiable;

public interface DataLoaderFactory {
    <T extends Identifiable> DataLoader<T> createDataLoader(Format format, Class<T> dataType);
}
