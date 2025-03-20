package org.vse.wallet.data;

import java.util.List;

public enum OperationType {
    EXPENSE, INCOME;

    public static List<String> listNames() {
        return List.of(EXPENSE.name(), INCOME.name());
    }
}
