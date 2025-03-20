package org.vse.wallet.data;

public abstract class Identifiable {
    private final long id;

    public Identifiable(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
