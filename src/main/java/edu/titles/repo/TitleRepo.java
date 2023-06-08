package edu.titles.repo;

import edu.titles.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TitleRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Title> rowMapper = (rs, i) -> new Title(
            rs.getInt("title_id"),
            rs.getString("name"),
            rs.getDouble("budget"),
            rs.getInt("runtime"),
            rs.getDouble("box_office"),
            rs.getDate("premiere_date").toLocalDate()
    );

    public List<Title> findAll() {
        return jdbcTemplate.query("""
                SELECT
                    title_id,
                    name,
                    budget,
                    premiere_date,
                    runtime,
                    box_office
                FROM title
                """, rowMapper);
    }

    public Title upsert(Title title) {
        var result = jdbcTemplate.query("""
                        INSERT INTO title(title_id, name, budget, premiere_date, runtime, box_office)
                        VALUES (:titleId, :name, :budget, :premiere_date, :runtime, :box_office)
                        ON CONFLICT (title_id) DO UPDATE
                            SET name = :name,
                                budget = :budget,
                                premiere_date = :premiere_date,
                                runtime = :runtime,
                                box_office = :box_office
                        RETURNING *
                        """,
                Map.of(
                        "titleId", title.getTitleId(),
                        "name", title.getName(),
                        "budget", title.getBudget(),
                        "premiere_date", title.getPremiereDate(),
                        "runtime", title.getRuntime(),
                        "box_office", title.getBoxOffice()
                ), rowMapper);
        return Optional.ofNullable(result.get(0)).orElseThrow();
    }

    public Optional<Title> findById(Integer titleId) {
        var result = jdbcTemplate.query("""
                        SELECT
                            title_id,
                            name,
                            budget,
                            premiere_date,
                            runtime,
                            box_office
                        FROM title
                        WHERE title_id = :titleId
                        """,
                Map.of(
                        "titleId", titleId
                ), rowMapper);
        return Optional.ofNullable(result.get(0));
    }

    public void deleteById(Integer titleId) {
        jdbcTemplate.update("""
                        DELETE FROM title
                        WHERE title_id = :titleId
                        """,
                Map.of(
                        "titleId", titleId));
    }
}
