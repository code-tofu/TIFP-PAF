package sg.edu.nus.iss.pafrsvp.repository;

public class Queries {
    public static final String SQL_INSERT_RSVP="""
        insert into rsvp(name,email,phone,confirmation_date,
        comments,food_type) values (?,?,?,?,?,?)
    """;
}
