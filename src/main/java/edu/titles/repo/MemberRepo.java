package edu.titles.repo;

import edu.titles.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Member> memberRowMapper = (rs, i) -> new Member(
            rs.getInt("member_id"),
            rs.getString("login"),
            rs.getString("password")
    );


    public Boolean existsByLogin(String login) {
        var maybeCount = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) AS count
                FROM member
                WHERE login = :login
                """, Map.of("login", login), Integer.class);
        return Optional.ofNullable(maybeCount).map(x -> x > 0).orElseThrow();
    }

    public Optional<Member> findByLogin(String login) {
        var result = jdbcTemplate.query("""
                SELECT member_id,
                       login,
                       password
                FROM member
                WHERE login = :login
                """, Map.of("login", login), memberRowMapper);
        return Optional.ofNullable(result.get(0));
    }

    public void save(Member member) {
    }
}
