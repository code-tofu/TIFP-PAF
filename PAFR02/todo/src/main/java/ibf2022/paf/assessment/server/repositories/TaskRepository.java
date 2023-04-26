package ibf2022.paf.assessment.server.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;
import static ibf2022.paf.assessment.server.repositories.DBQueries.*;

// Task 6
@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insertTask(Task newTask, String user_id) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement statement = conn.prepareStatement(INSERT_NEW_TASK, Statement.RETURN_GENERATED_KEYS);
            // "INSERT INTO items (description, priority, dueDate, user_id) VALUES (?, ?, ?,
            // ?)"
            statement.setString(1, newTask.getDescription());
            statement.setInt(2, newTask.getPriority());
            // public class java.sql.Date extends java.util.Date
            statement.setDate(3, Date.valueOf(newTask.getDueDate()));
            statement.setString(4, user_id);
            return statement;
        }, keyHolder);

        System.out.println("Task Inserted with Key#" + keyHolder.getKey().intValue());
        return true;
    }

}
