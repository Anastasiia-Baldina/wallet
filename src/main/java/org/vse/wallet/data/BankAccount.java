package org.vse.wallet.data;

import org.vse.wallet.file.DataVisitor;
import org.vse.wallet.utils.Asserts;

import javax.annotation.Nonnull;

public class BankAccount extends Identifiable implements DataElement {
    private final String name;
    private final long balance;

    private BankAccount(Builder b) {
        super(b.id);
        name = Asserts.notEmpty(b.name, "name");
        balance = b.balance;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public long getBalance() {
        return balance;
    }

    public String toString() {
        return "\tId: %s, Name: %s, Balance: %s".formatted(getId(), name, balance);
    }

    @Override
    public void accept(DataVisitor visitor) {
        visitor.visitBankAccount(this);
    }

    public Builder toBuilder() {
        return builder()
                .setId(getId())
                .setName(name)
                .setBalance(balance);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String name;
        private long balance;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBalance(long balance) {
            this.balance = balance;
            return this;
        }

        public BankAccount build() {
            return new BankAccount(this);
        }
    }
}
