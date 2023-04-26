package ibf2022.paf.assessment.server.repositories;

public class DBQueries {

    public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username= ?";
    public static final String INSERT_NEW_USER = "INSERT INTO users (user_id , username , name) VALUES (?,?,?)";
    public static final String INSERT_NEW_TASK = "INSERT INTO task (description, priority, due_date, user_id) VALUES (?, ?, ?, ?)";
}
