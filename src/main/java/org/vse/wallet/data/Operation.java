package org.vse.wallet.data;

import org.vse.wallet.file.DataVisitor;
import org.vse.wallet.utils.Asserts;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

public class Operation extends Identifiable implements DataElement {
    private final OperationType type;
    private final long bankAccountId;
    private final long amount;
    private final Date date;
    private final String description;
    private final long categoryId;

    public Operation(Builder builder) {
        super(builder.id);
        type = Asserts.notNull(builder.type, "type");
        bankAccountId = Asserts.notNull(builder.bankAccountId, "bankAccountId");
        amount = Asserts.notNull(builder.amount, "amount");
        if (type == OperationType.INCOME && amount < 0) {
            throw new IllegalArgumentException("Amount should be greater than 0");
        } else if (type == OperationType.EXPENSE && amount > 0) {
            throw new IllegalArgumentException("Amount should be less than 0");
        }
        date = Asserts.notNull(builder.date, "date");
        description = !builder.description.isEmpty() ? builder.description : null;
        categoryId = Asserts.notNull(builder.categoryId, "categoryId");
    }

    @Nonnull
    public OperationType getType() {
        return type;
    }

    public long getBankAccountId() {
        return bankAccountId;
    }

    public long getAmount() {
        return amount;
    }

    @Nonnull
    public Date getDate() {
        return date;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public long getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "Id=" + getId() +
                ", type=" + type +
                ", bankAccountId=" + bankAccountId +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId;
    }

    @Override
    public void accept(DataVisitor visitor) {
        visitor.visitOperation(this);
    }

    public Builder toBuilder() {
        return new Builder()
                .setId(getId())
                .setType(type)
                .setBankAccountId(bankAccountId)
                .setAmount(amount)
                .setDate(date)
                .setDescription(description)
                .setCategoryId(categoryId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private OperationType type;
        private Long bankAccountId;
        private Long amount;
        private Date date;
        private String description;
        private Long categoryId;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setType(OperationType type) {
            this.type = type;
            return this;
        }

        public Builder setBankAccountId(long bankAccountId) {
            this.bankAccountId = bankAccountId;
            return this;
        }

        public Builder setAmount(long amount) {
            this.amount = amount;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCategoryId(long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Operation build() {
            return new Operation(this);
        }
    }
}
