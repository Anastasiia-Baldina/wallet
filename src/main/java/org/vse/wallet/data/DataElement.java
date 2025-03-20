package org.vse.wallet.data;

import org.vse.wallet.file.DataVisitor;

public interface DataElement {
    void accept(DataVisitor visitor);
}
