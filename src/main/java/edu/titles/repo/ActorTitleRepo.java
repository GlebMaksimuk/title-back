package edu.titles.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ActorTitleRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void rebindDirectorAndTitle(Integer titleId, Integer actorId) {
        jdbcTemplate.update("""
                INSERT INTO actor_title(actor_id, title_id)
                VALUES (:actorId, :titleId)
                ON CONFLICT DO NOTHING
                """, Map.of("actorId", actorId, "titleId", titleId));
    }

    public void unbindTitle(Integer titleId) {
        jdbcTemplate.update("""
                DELETE FROM actor_title
                WHERE title_id = :titleId
                """, Map.of("titleId", titleId));
    }
}
