package org.vse.wallet.command;

public enum CommandType {
    LIST_BANK_ACCOUNT("List bank account"),
    CREATE_BANK_ACCOUNT("New bank account"),
    EDIT_BANK_ACCOUNT("Edit bank account"),
    DELETE_BANK_ACCOUNT("Delete bank account"),
    RECALCULATE_BANK_ACCOUNT("Recalculate bank account balance"),
    PRINT_BALANCE("Print balance for the period"),
    PRINT_CATEGORY_STATS("Print balance by category"),
    LIST_CATEGORY("List category"),
    CREATE_CATEGORY("New category"),
    EDIT_CATEGORY("Edit category"),
    DELETE_CATEGORY("Delete category"),
    LIST_OPERATION("List operation"),
    CREATE_OPERATION("New operation"),
    EDIT_OPERATION("Edit operation"),
    DELETE_OPERATION("Delete operation"),
    BACKUP("Backup data to files"),
    RESTORE("Restore data from files");

    private final String description;

    CommandType(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
