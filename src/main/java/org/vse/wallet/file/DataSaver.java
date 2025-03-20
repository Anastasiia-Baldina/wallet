package org.vse.wallet.file;

import org.vse.wallet.data.DataElement;

public class DataSaver {
    private final StorageVisitorFactory factory;

    public DataSaver(StorageVisitorFactory factory) {
        this.factory = factory;
    }

    public void save(DataElement element, Format format) {
        var visitor = factory.get(format);
        element.accept(visitor);
    }
}
