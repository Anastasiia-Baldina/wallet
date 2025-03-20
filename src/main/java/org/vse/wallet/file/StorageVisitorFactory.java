package org.vse.wallet.file;

public interface StorageVisitorFactory {
    DataVisitor get(Format format);
}
