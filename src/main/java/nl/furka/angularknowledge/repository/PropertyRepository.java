package nl.furka.angularknowledge.repository;

import nl.furka.angularknowledge.dto.CreatePropertyRequest;
import nl.furka.angularknowledge.dto.PropertyResponse;
import nl.furka.angularknowledge.model.Ingredient;
import nl.furka.angularknowledge.model.filter.PropertyFilter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.furka.angularknowledge.util.SqlUtil.buildWhereClause;

@Repository
public class PropertyRepository {
    private final JdbcClient jdbcClient;

    PropertyRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<PropertyResponse> getAllProperties(PropertyFilter filter) {
        String sql = """
                SELECT *
                FROM properties
                WHERE 1=1
                """;

        Map<String, Object> params = new HashMap<>();

        return jdbcClient.sql(sql + buildWhereClause(filter, params))
                .params(params)
                .query(propertyRowMapper())
                .list();
    }

    public List<PropertyResponse> getAllProperties() {
        String sql = """
                SELECT *
                FROM properties
                WHERE 1=1
                """;

        return jdbcClient.sql(sql)
                .query(propertyRowMapper())
                .list();
    }

    public List<PropertyResponse> getPropertiesByIngredientId(Integer ingredientId) {
        String sql = """
                SELECT p.* FROM properties p
                JOIN ingredient_properties ip ON p.id = ip.property_id
                WHERE ip.ingredient_id = :ingredientId
                """;

        return jdbcClient.sql(sql)
                .param("ingredientId", ingredientId)
                .query(propertyRowMapper())
                .list();
    }

    public PropertyResponse getPropertyById(Integer id) {
        String sql = """
                SELECT *
                FROM properties
                WHERE id = :id
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(propertyRowMapper())
                .single();
    }

    public PropertyResponse addProperty(CreatePropertyRequest property) {
        String sql = """
                INSERT INTO properties (name, unit_id)
                VALUES(?,?)
                RETURNING *
                """;
        return jdbcClient.sql(sql)
                .param(property.name())
                .param(property.unitId())
                .query(propertyRowMapper())
                .single();
    }

    public PropertyResponse deletePropertyById(Integer id) {
        String sql = """
                DELETE
                FROM properties
                WHERE id = :id
                RETURNING *
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(propertyRowMapper())
                .single();
    }

    private RowMapper<PropertyResponse> propertyRowMapper() {
        return (r, _) -> new PropertyResponse(
                r.getInt("id"),
                r.getString("name"),
                r.getInt("unit_id")
        );
    }
}
