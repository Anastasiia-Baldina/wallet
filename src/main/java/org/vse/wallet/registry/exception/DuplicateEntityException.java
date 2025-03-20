package org.vse.wallet.registry.exception;

import org.vse.wallet.data.Identifiable;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(Identifiable entity) {
        super("%s with id %s already exists".formatted(entity.getClass().getSimpleName(), entity.getId()));
    }
}
