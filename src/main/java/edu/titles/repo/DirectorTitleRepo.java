package edu.titles.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DirectorTitleRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void rebindDirectorAndTitle(Integer titleId, Integer directorId) {
        jdbcTemplate.update("""
                INSERT INTO director_title(director_id, title_id)
                VALUES (:directorId, :titleId)
                ON CONFLICT DO NOTHING
                """, Map.of("directorId", directorId, "titleId", titleId));
    }

    public void unbindTitle(Integer titleId) {
        jdbcTemplate.update("""
                DELETE FROM director_title
                WHERE title_id = :titleId
                """, Map.of("titleId", titleId));
    }
}
