package org.vse.wallet.registry.h2;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.vse.wallet.data.Operation;
import org.vse.wallet.data.OperationType;
import org.vse.wallet.registry.OperationRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DbOperationRegistry implements OperationRegistry {
    private static final String INSERT = "INSERT INTO operation " +
            "(id, bank_account_id, category_id, date, amount, description, type) " +
            "VALUES " +
            "(:id, :bankAccountId, :categoryId, :date, :amount, :description, :type)";
    private static final String UPDATE = "UPDATE operation SET " +
            "bank_account_id = :bankAccountId, " +
            "category_id = :categoryId, " +
            "date = :date, " +
            "amount = :amount, " +
            "description = :description, " +
            "type = :type " +
            "WHERE id = :id";
    private static final String REMOVE_ALL = "DELETE FROM operation";
    private static final String FIND_BY_ID = "SELECT * FROM operation WHERE id = :id";
    private static final String FIND_BY_BANK_ACCOUNT_ID = "SELECT * FROM operation WHERE bank_account_id = :bankAccountId";
    private static final String FIND_BY_CATEGORY_ID = "SELECT * FROM operation WHERE category_id = :categoryId";
    private static final String FIND_ALL = "SELECT * FROM operation";
    private static final String REMOVE_BY_ID = "DELETE FROM operation WHERE id = :id";
    private static final String REMOVE_BY_BANK_ACCOUNT_ID = "DELETE FROM operation WHERE bank_account_id = :bankAccountId";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DbOperationRegistry(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Operation operation) {
        var sqlParams = new MapSqlParameterSource()
                .addValue("id", operation.getId())
                .addValue("bankAccountId", operation.getBankAccountId())
                .addValue("categoryId", operation.getCategoryId())
                .addValue("date", operation.getDate())
                .addValue("amount", operation.getAmount())
                .addValue("description", operation.getDescription())
                .addValue("type", operation.getType().name());
        jdbcTemplate.update(INSERT, sqlParams);
    }

    @Override
    public void update(Operation operation) {
        var sqlParams = new MapSqlParameterSource()
                .addValue("id", operation.getId())
                .addValue("bankAccountId", operation.getBankAccountId())
                .addValue("categoryId", operation.getCategoryId())
                .addValue("date", operation.getDate())
                .addValue("amount", operation.getAmount())
                .addValue("description", operation.getDescription())
                .addValue("type", operation.getType().name());
        jdbcTemplate.update(UPDATE, sqlParams);
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(REMOVE_ALL, EmptySqlParameterSource.INSTANCE);
    }

    @Nullable
    @Override
    public Operation findById(long id) {
        var sqlParams = new MapSqlParameterSource("id", id);
        var data = jdbcTemplate.query(FIND_BY_ID, sqlParams, OperationRowMapper.INSTANCE);
        return data.isEmpty() ? null : data.get(0);
    }

    @Nonnull
    @Override
    public List<Operation> findByBankAccountId(long bankAccountId) {
        var sqlParams = new MapSqlParameterSource("bankAccountId", bankAccountId);
        return jdbcTemplate.query(FIND_BY_BANK_ACCOUNT_ID, sqlParams, OperationRowMapper.INSTANCE);
    }

    @Nonnull
    @Override
    public List<Operation> findByCategoryId(long categoryId) {
        var sqlParams = new MapSqlParameterSource("categoryId", categoryId);
        return jdbcTemplate.query(FIND_BY_CATEGORY_ID, sqlParams, OperationRowMapper.INSTANCE);
    }

    @Nonnull
    @Override
    public List<Operation> findAll() {
        return jdbcTemplate.query(FIND_ALL, OperationRowMapper.INSTANCE);
    }

    @Override
    public void removeById(long id) {
        var sqlParams = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(REMOVE_BY_ID, sqlParams);
    }

    @Override
    public void removeByBankAccountId(long bankAccountId) {
        var sqlParams = new MapSqlParameterSource("bankAccountId", bankAccountId);
        jdbcTemplate.update(REMOVE_BY_BANK_ACCOUNT_ID, sqlParams);
    }

    private static class OperationRowMapper implements RowMapper<Operation> {
        static final OperationRowMapper INSTANCE = new OperationRowMapper();

        @Override
        @Nonnull
        public Operation mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            return Operation.builder()
                    .setId(rs.getLong("id"))
                    .setBankAccountId(rs.getLong("bank_account_id"))
                    .setCategoryId(rs.getLong("category_id"))
                    .setDate(new Date(rs.getDate("date").getTime()))
                    .setAmount(rs.getLong("amount"))
                    .setDescription(rs.getString("description"))
                    .setType(OperationType.valueOf(rs.getString("type")))
                    .build();
        }
    }
}
