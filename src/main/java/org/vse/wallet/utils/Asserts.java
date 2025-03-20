package org.vse.wallet.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public final class Asserts {
    private Asserts() {
    }

    @Nonnull
    public static <T> T notNull(T parameter, String paramName) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter '" + paramName + "' is null.");
        }
        return parameter;
    }

    @Nonnull
    public static String notEmpty(String parameter, String paramName) {
        if (notNull(parameter, paramName).isEmpty()) {
            throw new IllegalArgumentException("Parameter '" + paramName + "' must not be empty");
        }

        return parameter;
    }

    @Nonnull
    public static <T, C extends Collection<T>> C notEmpty(C param, String paramName) {
        if (notNull(param, paramName).isEmpty()) {
            throw new IllegalArgumentException("Parameter [" + paramName + "] must not be empty");
        }
        return param;
    }

    public static long isLong(Object parameter, String paramName) {
        if (parameter instanceof Long longValue) {
            return longValue;
        }
        throw new IllegalArgumentException("Parameter '" + paramName + "' must be numeric");
    }

    @Nullable
    public static String isString(Object parameter, String paramName) {
        if (parameter == null) {
            return null;
        } else if (parameter.getClass() == String.class) {
            return (String) parameter;
        }
        throw new IllegalArgumentException("Parameter '" + paramName + "' must be string");
    }
}
