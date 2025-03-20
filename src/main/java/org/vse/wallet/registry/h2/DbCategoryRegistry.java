package org.vse.wallet.registry.h2;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.vse.wallet.data.Category;
import org.vse.wallet.data.CategoryType;
import org.vse.wallet.registry.CategoryRegistry;
import org.vse.wallet.registry.exception.EntityNotExistsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DbCategoryRegistry implements CategoryRegistry {
    private static final String INSERT = "INSERT INTO category (id, type, name) VALUES (:id, :type, :name)";
    private static final String UPDATE = "UPDATE category SET type = :type, name = :name WHERE id = :id";
    private static final String FIND_ALL = "SELECT * FROM category";
    private static final String FIND_BY_ID = "SELECT * FROM category WHERE id = :id";
    private static final String REMOVE_ALL = "DELETE FROM category";
    private static final String REMOVE_BY_ID = "DELETE FROM category WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DbCategoryRegistry(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Category category) {
        var sqlParams = new MapSqlParameterSource()
                .addValue("id", category.getId())
                .addValue("type", category.getType().name())
                .addValue("name", category.getName());
        jdbcTemplate.update(INSERT, sqlParams);
    }

    @Override
    public void update(Category category) {
        var sqlParams = new MapSqlParameterSource()
                .addValue("id", category.getId())
                .addValue("type", category.getType().name())
                .addValue("name", category.getName());
        if (jdbcTemplate.update(UPDATE, sqlParams) == 0) {
            throw new EntityNotExistsException(category);
        }
    }

    @Override
    @Nonnull
    public List<Category> findAll() {
        return jdbcTemplate.query(FIND_ALL, EmptySqlParameterSource.INSTANCE, CategoryRowMapper.INSTANCE);
    }

    @Override
    @Nullable
    public Category findById(long id) {
        var sqlParams = new MapSqlParameterSource("id", id);
        var data = jdbcTemplate.query(FIND_BY_ID, sqlParams, CategoryRowMapper.INSTANCE);
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

    private static class CategoryRowMapper implements RowMapper<Category> {
        static CategoryRowMapper INSTANCE = new CategoryRowMapper();

        @Override
        public Category mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            return Category.builder()
                    .setId(rs.getLong("id"))
                    .setType(CategoryType.valueOf(rs.getString("type")))
                    .setName(rs.getString("name"))
                    .build();
        }
    }
}
