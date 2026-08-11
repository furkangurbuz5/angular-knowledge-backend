package nl.furka.angularknowledge.repository;

import nl.furka.angularknowledge.dto.CreatePersonRequest;
import nl.furka.angularknowledge.dto.IngredientPropertiesResponse;
import nl.furka.angularknowledge.dto.PersonIngredientsResponse;
import nl.furka.angularknowledge.dto.UpdatePersonRequest;
import nl.furka.angularknowledge.model.Person;
import nl.furka.angularknowledge.model.filter.PersonFilter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.furka.angularknowledge.util.SqlUtil.buildWhereClause;

@Repository
public class PersonRepository {
    private final JdbcClient jdbcClient;

    PersonRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Person> getAllPersons(PersonFilter filter) {
        String sql = """
                SELECT *
                FROM person
                WHERE 1=1
                """;
        Map<String, Object> params = new HashMap<>();

        return jdbcClient.sql(sql + buildWhereClause(filter, params))
                .params(params)
                .query(personRowMapper())
                .list();
    }

    public Person getPersonById(Integer id) {
        String sql = """
                SELECT *
                FROM person
                WHERE id = :id
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(personRowMapper())
                .single();
    }

    public Person addPerson(CreatePersonRequest person) {
        String sql = """
                INSERT INTO person (first_name, last_name, email, car, city, country_of_origin, bank)
                VALUES(?,?,?,?,?,?,?)
                RETURNING *
                """;
        return jdbcClient.sql(sql)
                .param(person.firstName())
                .param(person.lastName())
                .param(person.email())
                .param(person.car())
                .param(person.city())
                .param(person.countryOfOrigin())
                .param(person.bank())
                .query(personRowMapper())
                .single();
    }

    public Person deletePersonById(Integer id) {
        String sql = """
                DELETE
                FROM person
                WHERE id = :id
                RETURNING *
                """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(personRowMapper())
                .single();
    }

    public Person updatePerson(Integer id, UpdatePersonRequest req) {
        var setClauses = new java.util.ArrayList<String>();
        var params = new java.util.HashMap<String, Object>();

        if (req.firstName() != null) {
            setClauses.add("first_name = :firstName");
            params.put("firstName", req.firstName());
        }
        if (req.lastName() != null) {
            setClauses.add("last_name = :lastName");
            params.put("lastName", req.lastName());
        }
        if (req.email() != null) {
            setClauses.add("email = :email");
            params.put("email", req.email());
        }
        if (req.car() != null) {
            setClauses.add("car = :car");
            params.put("car", req.car());
        }
        if (req.city() != null) {
            setClauses.add("city = :city");
            params.put("city", req.city());
        }
        if (req.countryOfOrigin() != null) {
            setClauses.add("country_of_origin = :countryOfOrigin");
            params.put("countryOfOrigin", req.countryOfOrigin());
        }
        if (req.bank() != null) {
            setClauses.add("bank = :bank");
            params.put("bank", req.bank());
        }

        if (setClauses.isEmpty()) {
            return jdbcClient.sql("SELECT * FROM person WHERE id = :id")
                    .param("id", id)
                    .query(personRowMapper())
                    .single();
        }

        params.put("id", id);

        String sql = "UPDATE person SET " + String.join(", ", setClauses) +
                " WHERE id = :id RETURNING *";

        return jdbcClient.sql(sql)
                .params(params)
                .query(personRowMapper())
                .single();
    }

    public List<PersonIngredientsResponse> getPersonIngredientsById(Integer id) {
        String sql = """
                SELECT *
                FROM person_ingredients
                WHERE person_id = :id
                """;

        return jdbcClient.sql(sql)
                .param("id", id)
                .query(personIngredientsRowMapper())
                .list();
    }

    private RowMapper<Person> personRowMapper() {
        return (r, _) -> new Person(
                r.getInt("id"),
                r.getString("first_name"),
                r.getString("last_name"),
                r.getString("email"),
                r.getString("car"),
                r.getString("city"),
                r.getString("country_of_origin"),
                r.getString("bank")
        );
    }

    private RowMapper<PersonIngredientsResponse> personIngredientsRowMapper() {
        return (r, _) -> new PersonIngredientsResponse(
                r.getInt("id"),
                r.getInt("person_id"),
                r.getInt("ingredient_id")
        );
    }
}
