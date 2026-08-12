package nl.furka.angularknowledge.repository;

import nl.furka.angularknowledge.dto.CreatePropertyRequest;
import nl.furka.angularknowledge.dto.PropertyResponse;
import nl.furka.angularknowledge.dto.PropertyWithValueResponse;
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
                .query(propertyResponseRowMapper())
                .list();
    }

    public List<PropertyResponse> getAllProperties() {
        String sql = """
                SELECT *
                FROM properties
                WHERE 1=1
                """;

        return jdbcClient.sql(sql)
                .query(propertyResponseRowMapper())
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
                .query(propertyResponseRowMapper())
                .list();
    }

    public List<PropertyWithValueResponse> getPropertiesWithValueByIngredientId(Integer ingredientId) {
        String sql = """
                SELECT
                    p.id,
                    p.name AS name,
                    unit_id,
                    ip.value
                FROM
                    properties p
                JOIN
                    ingredient_properties ip ON p.id = ip.property_id
                WHERE
                    ip.ingredient_id = :ingredientId;
                """;

        return jdbcClient.sql(sql)
                .param("ingredientId", ingredientId)
                .query(propertyWithValueResponseRowMapper())
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
                .query(propertyResponseRowMapper())
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
                .query(propertyResponseRowMapper())
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
                .query(propertyResponseRowMapper())
                .single();
    }

    private RowMapper<PropertyResponse> propertyResponseRowMapper() {
        return (r, _) -> new PropertyResponse(
                r.getInt("id"),
                r.getString("name"),
                r.getInt("unit_id")
        );
    }

    private RowMapper<PropertyWithValueResponse> propertyWithValueResponseRowMapper() {
        return (r, _) -> new PropertyWithValueResponse(
                r.getInt("id"),
                r.getString("name"),
                r.getInt("unit_id"),
                r.getInt("value")
        );
    }
}
