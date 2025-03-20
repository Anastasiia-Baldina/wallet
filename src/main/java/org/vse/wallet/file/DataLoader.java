package org.vse.wallet.file;

import org.vse.wallet.data.Identifiable;

import java.util.List;

public interface DataLoader <T extends Identifiable> {
    List<T> load();
}
