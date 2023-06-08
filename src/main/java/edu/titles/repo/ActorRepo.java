package edu.titles.repo;

import edu.titles.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ActorRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Actor> rowMapper = ((rs, rowNum) ->
            new Actor(
                    rs.getInt("actor_id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getDate("birth_date").toLocalDate()
            ));

    public List<Actor> findAll() {
        return jdbcTemplate.query("""
                SELECT actor_id,
                       name,
                       surname,
                       birth_date
                       FROM actor
                """, rowMapper);
    }

    public List<Actor> findAllForTitle(Integer titleId) {
        return jdbcTemplate.query("""
                        SELECT a.actor_id,
                               a.name,
                               a.surname,
                               a.birth_date
                               FROM actor a
                               LEFT JOIN actor_title at ON a.actor_id = at.actor_id
                               LEFT JOIN title t ON at.title_id = t.title_id
                               WHERE t.title_id = :titleId
                               GROUP BY a.actor_id
                        """,
                Map.of("titleId", titleId),
                rowMapper);
    }

    public Optional<Actor> findById(Integer actorId) {
        var result = jdbcTemplate.query("""
                SELECT
                    a.actor_id,
                    a.name,
                    a.surname,
                    a.birth_date
                FROM actor a
                WHERE a.actor_id = :actorId
                """, Map.of("actorId", actorId), rowMapper);
        return Optional.ofNullable(result.get(0));
    }

    public Actor upsert(Actor actor) {
        List<Actor> result;
        if (actor.getActorId() != null) {
            result = jdbcTemplate.query("""
                            INSERT INTO actor(actor_id, name, surname, birth_date)
                            VALUES (:actorId, :name, :surname, :birthDate)
                            ON CONFLICT (actor_id) DO UPDATE
                                SET name = :name,
                                    surname = :surname,
                                    birth_date = :birthDate
                            RETURNING *
                            """,
                    Map.of(
                            "actorId", actor.getActorId(),
                            "name", actor.getName(),
                            "surname", actor.getSurname(),
                            "birthDate", actor.getBirthDate()),
                    rowMapper);
        } else {
            result = jdbcTemplate.query("""
                            INSERT INTO actor(name, surname, birth_date)
                            VALUES (:name, :surname, :birthDate)
                            RETURNING *
                            """,
                    Map.of(
                            "name", actor.getName(),
                            "surname", actor.getSurname(),
                            "birthDate", actor.getBirthDate()),
                    rowMapper);
        }
        return Optional.ofNullable(result.get(0)).orElseThrow();
    }

    public void deleteById(Integer actorId) {
        jdbcTemplate.update("""
                        DELETE FROM actor
                        WHERE actor_id = :actorId
                        """,
                Map.of(
                        "actorId", actorId));
    }
}
