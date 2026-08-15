package nl.furka.angularknowledge.repository;

import nl.furka.angularknowledge.dto.CollectionResponse;
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

    public RowMapper<CollectionResponse> collectionResponseRowMapper() {
        return (rs, _) -> new CollectionResponse(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

}
