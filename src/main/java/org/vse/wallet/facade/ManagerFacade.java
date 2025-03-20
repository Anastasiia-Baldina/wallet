package org.vse.wallet.facade;

public interface ManagerFacade {
    void recalculateBalance();

    void printPeriodBalance();

    void printCategoryStats();

    void backupData();

    void restoreData();
}
