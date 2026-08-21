package nl.furka.angularknowledge.repository;

import nl.furka.angularknowledge.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CollectionRepository {
    private final JdbcClient jdbcClient;
    private final Logger logger = LoggerFactory.getLogger(CollectionRepository.class);

    CollectionRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<CollectionResponse> getAllCollections() {
        String sql = """
                SELECT * FROM collections;
                """;

        return jdbcClient
                .sql(sql)
                .query(collectionResponseRowMapper())
                .list();
    }

    public CollectionResponse getCollectionById(Integer collectionId) {
        String sql = """
                SELECT * FROM collections
                WHERE id = :collectionId;
                """;

        return jdbcClient
                .sql(sql)
                .param("collectionId", collectionId)
                .query(collectionResponseRowMapper())
                .single();
    }

    public CollectionResponse addCollection(CreateCollectionRequest request) {
        String sql = """
                INSERT INTO collections (name)
                VALUES (?)
                RETURNING *
                """;

        return jdbcClient
                .sql(sql)
                .param(request.name())
                .query(collectionResponseRowMapper())
                .single();
    }

    @Transactional
    public void deleteCollectionById(Integer collectionId) {
        String sql1 = """
                DELETE FROM collection_ingredients
                WHERE collection_id = :collectionId;
                """;

        jdbcClient
                .sql(sql1)
                .param("collectionId", collectionId)
                .update();

        String sql2 = """
                DELETE FROM collections
                WHERE id = :collectionId;
                """;
        jdbcClient
                .sql(sql2)
                .param("collectionId", collectionId)
                .update();
    }

    public CollectionIngredientsResponse addFoodToCollection(Integer collectionId, AddFoodToCollectionRequest request) {
        String sql = """
                INSERT INTO collection_ingredients (collection_id, ingredient_id, quantity)
                VALUES (?,?,?)
                RETURNING *
                """;

        return jdbcClient
                .sql(sql)
                .param(collectionId)
                .param(request.ingredientId())
                .param(request.quantity())
                .query(collectionIngredientsResponseRowMapper())
                .single();
    }

    public Optional<CollectionIngredientsResponse> deleteFoodFromCollection(Integer collectionId, DeleteFoodFromCollectionRequest request) throws EmptyResultDataAccessException {
        String sql = """
                DELETE FROM collection_ingredients
                WHERE collection_id = :collectionId
                AND ingredient_id = :ingredientId
                RETURNING *
                """;

        return jdbcClient
                .sql(sql)
                .param("collectionId", collectionId)
                .param("ingredientId", request.ingredientId())
                .query(collectionIngredientsResponseRowMapper())
                .optional();
    }

    public List<CollectionPropertiesResponse> getCollectionProperties(Integer collectionId) {
        String sql = """
                SELECT
                    ip.property_id,
                    ci.quantity as collection_serving_amount,
                    i.serving_size,
                    i.unit_id,
                    ip.value as serving_amount
                FROM collection_ingredients ci
                    LEFT JOIN ingredients i ON i.id = ci.ingredient_id
                    LEFT JOIN ingredient_properties ip ON ci.ingredient_id = ip.ingredient_id
                WHERE ci.collection_id = :collectionId
                ORDER BY ip.property_id ASC;
                """;

        return jdbcClient.sql(sql)
                .param("collectionId", collectionId)
                .query(collectionPropertiesResponseRowMapper())
                .list();
    }

    private RowMapper<CollectionResponse> collectionResponseRowMapper() {
        return (rs, _) -> new CollectionResponse(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

    private RowMapper<CollectionIngredientsResponse> collectionIngredientsResponseRowMapper() {
        return (rs, _) -> new CollectionIngredientsResponse(
                rs.getInt("id"),
                rs.getInt("collection_id"),
                rs.getInt("ingredient_id"),
                rs.getInt("quantity")
        );
    }

    private RowMapper<CollectionPropertiesResponse> collectionPropertiesResponseRowMapper() {
        return (rs, _) -> new CollectionPropertiesResponse(
                rs.getInt("property_id"),
                rs.getInt("collection_serving_amount"),
                rs.getInt("serving_size"),
                rs.getInt("unit_id"),
                rs.getInt("serving_amount")
        );
    }
}
