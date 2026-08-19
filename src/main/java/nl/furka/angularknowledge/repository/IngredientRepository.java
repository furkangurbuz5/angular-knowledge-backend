package nl.furka.angularknowledge.repository;

import nl.furka.angularknowledge.dto.*;
import nl.furka.angularknowledge.model.filter.IngredientFilter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.furka.angularknowledge.util.SqlUtil.buildWhereClause;

@Repository
public class IngredientRepository {
    private final JdbcClient jdbcClient;

    IngredientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public IngredientResponse addIngredient(CreateIngredientRequest ingredient) {
        String sql = """
                INSERT INTO ingredients (name, serving_size, unit_id)
                VALUES(?,?,?)
                RETURNING *
                """;

        return jdbcClient.sql(sql)
                .param(ingredient.name())
                .param(ingredient.servingSize())
                .param(ingredient.unitId())
                .query(ingredientResponseRowMapper())
                .single();
    }

    public List<IngredientResponse> getIngredients(IngredientFilter filter) {
        String sql = """
                SELECT *
                FROM ingredients
                WHERE 1=1
                """;
        Map<String, Object> params = new HashMap<>();

        return jdbcClient.sql(sql + buildWhereClause(filter, params))
                .params(params)
                .query(ingredientResponseRowMapper())
                .list();

    }

    public List<IngredientResponse> getIngredients() {
        String sql = """
                SELECT *
                FROM ingredients
                WHERE 1=1
                """;

        return jdbcClient.sql(sql)
                .query(ingredientResponseRowMapper())
                .list();
    }

    public IngredientResponse getIngredientById(Integer ingredientId) {
        String sql = """
                SELECT *
                FROM ingredients
                WHERE id = :id
                """;

        return jdbcClient.sql(sql)
                .param("id", ingredientId)
                .query(ingredientResponseRowMapper())
                .single();
    }

    public List<IngredientPropertiesResponse> getIngredientProperties() {
        String sql = """
                SELECT *
                FROM ingredient_properties
                WHERE 1=1
                """;

        return jdbcClient.sql(sql)
                .query(ingredientPropertiesResponseRowMapper())
                .list();
    }

    public List<IngredientPropertiesResponse> getIngredientPropertiesByIngredientId(Integer ingredientId) {
        String sql = """
                SELECT *
                FROM ingredient_properties
                WHERE ingredient_id = :id
                """;

        return jdbcClient.sql(sql)
                .param("id", ingredientId)
                .query(ingredientPropertiesResponseRowMapper())
                .list();
    }

    public List<IngredientResponse> getIngredientsByPersonId(Integer personId) {
        String sql = """
                SELECT i.* FROM ingredients i
                JOIN person_ingredients pi ON i.id = pi.ingredient_id
                WHERE pi.person_id = :personId
                """;

        return jdbcClient.sql(sql)
                .param("personId", personId)
                .query(ingredientResponseRowMapper())
                .list();
    }

    public IngredientResponse deleteIngredientById(Integer id) {
        String sql = """
                DELETE
                FROM ingredients
                WHERE id = :id
                RETURNING *
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(ingredientResponseRowMapper())
                .single();
    }

    public IngredientPropertiesResponse addPropertyToIngredient(AddPropertyToIngredientRequest request) {
        String sql = """
                INSERT INTO ingredient_properties (ingredient_id, property_id, value)
                VALUES(?,?,?)
                RETURNING *
                """;

        return jdbcClient.sql(sql)
                .param(request.ingredientId())
                .param(request.propertyId())
                .param(request.value())
                .query(ingredientPropertiesResponseRowMapper())
                .single();
    }

    public List<IngredientWithQuantityResponse> getIngredientsByCollectionId(Integer collectionId) {
        String sql = """
                SELECT i.*, ci.quantity FROM ingredients i
                JOIN collection_ingredients ci ON i.id = ci.ingredient_id
                WHERE ci.collection_id = :collectionId
                """;

        return jdbcClient.sql(sql)
                .param("collectionId", collectionId)
                .query(ingredientWithQuantityResponseRowMapper())
                .list();
    }

    private RowMapper<IngredientResponse> ingredientResponseRowMapper() {
        return (rs, _) -> new IngredientResponse(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("serving_size"),
                rs.getInt("unit_id")
        );
    }

    private RowMapper<IngredientWithQuantityResponse> ingredientWithQuantityResponseRowMapper() {
        return (rs, _) -> new IngredientWithQuantityResponse(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("serving_size"),
                rs.getInt("unit_id"),
                rs.getInt("quantity")
        );
    }

    private RowMapper<IngredientPropertiesResponse> ingredientPropertiesResponseRowMapper() {
        return (rs, _) -> new IngredientPropertiesResponse(
                rs.getInt("id"),
                rs.getInt("ingredient_id"),
                rs.getInt("property_id"),
                rs.getInt("value")
        );
    }
}
