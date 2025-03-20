package org.vse.wallet.ui;

import org.vse.wallet.data.Identifiable;

public interface Editor<T extends Identifiable> {
    T create(long id);

    T edit(T entity);

    Class<T> getType();
}
