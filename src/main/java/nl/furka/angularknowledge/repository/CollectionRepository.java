package nl.furka.angularknowledge.repository;

import nl.furka.angularknowledge.dto.AddFoodToCollectionRequest;
import nl.furka.angularknowledge.dto.CollectionIngredientsResponse;
import nl.furka.angularknowledge.dto.CollectionResponse;
import nl.furka.angularknowledge.dto.CreateCollectionRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CollectionRepository {
    private final JdbcClient jdbcClient;

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

    public CollectionIngredientsResponse addFoodToCollection(Integer id, AddFoodToCollectionRequest request) {
        String sql = """
                INSERT INTO collection_ingredients (collection_id, ingredient_id, quantity)
                VALUES (?,?,?)
                RETURNING *
                """;

        return jdbcClient
                .sql(sql)
                .param(request.ingredientId())
                .param(request.quantity())
                .query(collectionIngredientsResponseRowMapper())
                .single();
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
