package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.utils.utils;

import static ibf2022.paf.assessment.server.repositories.DBQueries.*;

// Task 3
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findUserByUsername(String username) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_USER_BY_USERNAME, username);
        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            System.out.println(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public String insertUser(User user) throws Exception {
        String newId = utils.generateUUID(8);
        int result = jdbcTemplate.update(INSERT_NEW_USER, newId, user.getUsername(), user.getName());
        if (result == 1)
            return newId;

        // should i keep this?
        throw new Exception("Insert User Error");
    }
}
