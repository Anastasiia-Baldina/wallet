package org.vse.wallet.data;

import org.vse.wallet.file.DataVisitor;
import org.vse.wallet.utils.Asserts;

import javax.annotation.Nonnull;

public class Category extends Identifiable implements DataElement {
    private final CategoryType type;
    private final String name;

    private Category(Builder b) {
        super(b.id);
        type = Asserts.notNull(b.type, "type");
        name = Asserts.notEmpty(b.name, "name");
    }

    @Nonnull
    public CategoryType getType() {
        return type;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public String toString() {
        return "\tId: %s, Name: %s, Type: %s".formatted(getId(), name, type);
    }


    @Override
    public void accept(DataVisitor visitor) {
        visitor.visitCategory(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().setId(getId()).setType(type).setName(name);
    }

    public static class Builder {
        private long id;
        private CategoryType type;
        private String name;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setType(CategoryType type) {
            this.type = type;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
