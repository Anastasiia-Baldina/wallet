package org.vse.wallet.data;

import java.util.List;

public enum CategoryType {
    EXPENSE,
    INCOME;

    public static List<String> listNames() {
        return List.of(EXPENSE.name(), INCOME.name());
    }
}
