package org.vse.wallet.registry.exception;

import org.vse.wallet.data.Identifiable;

public class EntityNotExistsException extends RuntimeException {
    public EntityNotExistsException(Identifiable entity) {
        super("%s with id %d not exists".formatted(entity.getClass().getSimpleName(), entity.getId()));
    }
}
