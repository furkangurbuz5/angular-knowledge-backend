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

    public RowMapper<CollectionResponse> collectionResponseRowMapper() {
        return (rs, _) -> new CollectionResponse(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

    public RowMapper<CollectionIngredientsResponse> collectionIngredientsResponseRowMapper() {
        return (rs, _) -> new CollectionIngredientsResponse(
                rs.getInt("id"),
                rs.getInt("collection_id"),
                rs.getInt("ingredient_id"),
                rs.getInt("quantity")
        );
    }
}
