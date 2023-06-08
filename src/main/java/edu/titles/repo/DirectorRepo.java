package edu.titles.repo;

import edu.titles.model.Director;
import edu.titles.model.DirectorWithAverageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DirectorRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Director> rowMapper = ((rs, rowNum) ->
            new Director(
                    rs.getInt("director_id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getDate("birth_date").toLocalDate()
            ));

    private static final RowMapper<DirectorWithAverageParams> rowMapperAverage = ((rs, rowNum) ->
            new DirectorWithAverageParams(
                    rowMapper.mapRow(rs, rowNum),
                    rs.getDouble("profit_multiplier"),
                    rs.getDouble("profit_average")
            ));

    public List<DirectorWithAverageParams> findDirectorCalculatingAverageParams() {
        return jdbcTemplate.query("""
                SELECT d.director_id,
                       d.name,
                       d.surname,
                       d.birth_date,
                       AVG(box_office / budget) AS profit_multiplier,
                       AVG(box_office - budget) AS profit_average
                       FROM director d
                       LEFT JOIN director_title dt ON d.director_id = dt.director_id
                       LEFT JOIN title t ON dt.title_id = t.title_id
                       GROUP BY d.director_id
                """, rowMapperAverage);
    }

    public List<Director> findAllForTitle(Integer titleId) {
        return jdbcTemplate.query("""
                        SELECT d.director_id,
                               d.name,
                               d.surname,
                               d.birth_date
                               FROM director d
                               LEFT JOIN director_title dt ON d.director_id = dt.director_id
                               LEFT JOIN title t ON dt.title_id = t.title_id
                               WHERE t.title_id = :titleId
                               GROUP BY d.director_id
                        """,
                Map.of("titleId", titleId),
                rowMapper);
    }

    public Optional<Director> findById(Integer directorId) {
        var result = jdbcTemplate.query("""
                SELECT
                    d.director_id,
                    d.name,
                    d.surname,
                    d.birth_date
                FROM director d
                WHERE d.director_id = :directorId
                """, Map.of("directorId", directorId), rowMapper);
        return Optional.ofNullable(result.get(0));
    }

    public Director upsert(Director director) {
        List<Director> result;
        if (director.getDirectorId() != null) {
            result = jdbcTemplate.query("""
                            INSERT INTO director(director_id, name, surname, birth_date)
                            VALUES (:directorId, :name, :surname, :birthDate)
                            ON CONFLICT (director_id) DO UPDATE
                                SET name = :name,
                                    surname = :surname,
                                    birth_date = :birthDate
                            RETURNING *
                            """,
                    Map.of(
                            "directorId", director.getDirectorId(),
                            "name", director.getName(),
                            "surname", director.getSurname(),
                            "birthDate", director.getBirthDate()),
                    rowMapper);
        } else {
            result = jdbcTemplate.query("""
                            INSERT INTO director(name, surname, birth_date)
                            VALUES (:name, :surname, :birthDate)
                            RETURNING *
                            """,
                    Map.of(
                            "name", director.getName(),
                            "surname", director.getSurname(),
                            "birthDate", director.getBirthDate()),
                    rowMapper);
        }
        return Optional.ofNullable(result.get(0)).orElseThrow();
    }

    public void deleteById(Integer directorId) {
        jdbcTemplate.update("""
                        DELETE FROM director
                        WHERE director_id = :directorId
                        """,
                Map.of(
                        "directorId", directorId));
    }
}
