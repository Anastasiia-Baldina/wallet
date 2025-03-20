package org.vse.wallet.registry.h2;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.vse.wallet.data.BankAccount;
import org.vse.wallet.registry.BankAccountRegistry;
import org.vse.wallet.registry.exception.EntityNotExistsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DbBankAccountRegistry implements BankAccountRegistry {
    private static final String REMOVE_ALL = "DELETE FROM bank_account";
    private static final String FIND_ALL = "SELECT * FROM bank_account";
    private static final String FIND_BY_ID = "SELECT * FROM bank_account WHERE id = :id";
    private static final String INSERT = "INSERT INTO bank_account (id, name, balance) VALUES (:id, :name, :balance)";
    private static final String UPDATE = "UPDATE bank_account SET name = :name, balance = :balance WHERE id = :id";
    private static final String REMOVE_BY_ID = "DELETE FROM bank_account WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DbBankAccountRegistry(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(BankAccount bankAccount) {
        var sqlParams = new MapSqlParameterSource()
                .addValue("id", bankAccount.getId())
                .addValue("name", bankAccount.getName())
                .addValue("balance", bankAccount.getBalance());
        jdbcTemplate.update(INSERT, sqlParams);
    }

    @Override
    public void update(BankAccount bankAccount) {
        var sqlParams = new MapSqlParameterSource()
                .addValue("id", bankAccount.getId())
                .addValue("name", bankAccount.getName())
                .addValue("balance", bankAccount.getBalance());
        if (jdbcTemplate.update(UPDATE, sqlParams) == 0) {
            throw new EntityNotExistsException(bankAccount);
        }
    }

    @Nonnull
    @Override
    public List<BankAccount> findAll() {
        return jdbcTemplate.query(FIND_ALL, BankAccountRowMapper.INSTANCE);
    }

    @Nullable
    @Override
    public BankAccount findById(Long id) {
        var sqlParams = new MapSqlParameterSource("id", id);
        var data = jdbcTemplate.query(FIND_BY_ID, sqlParams, BankAccountRowMapper.INSTANCE);

        return data.isEmpty() ? null : data.get(0);
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(REMOVE_ALL, EmptySqlParameterSource.INSTANCE);
    }

    @Override
    public void removeById(long id) {
        var sqlParams = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(REMOVE_BY_ID, sqlParams);
    }

    private static class BankAccountRowMapper implements RowMapper<BankAccount> {
        static final BankAccountRowMapper INSTANCE = new BankAccountRowMapper();

        @Override
        public BankAccount mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            return BankAccount.builder()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"))
                    .setBalance(rs.getLong("balance"))
                    .build();
        }
    }
}
