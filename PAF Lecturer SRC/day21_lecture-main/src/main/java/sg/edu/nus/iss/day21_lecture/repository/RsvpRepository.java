package sg.edu.nus.iss.day21_lecture.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day21_lecture.model.RSVP;

@Repository
public class RsvpRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String countSQL = "select count(*) from rsvp";

    String selectAllSQL = "select * from rsvp";

    String selectByIdSQL = "select * from rsvp where id = ?";

    String selectByNameSQL = "select * from rsvp where full_name like '%?%'";

    String insertSQL = "insert into rsvp (full_name, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)";

    String updateSQL = "update rsvp " +
            " set full_name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ? " +
            " where id = ?";

    public Integer countAll() {
        return jdbcTemplate.queryForObject(countSQL, Integer.class);
    }

    public List<RSVP> findAll() {
        return jdbcTemplate.query(selectAllSQL, BeanPropertyRowMapper.newInstance(RSVP.class));
    }

    public RSVP findById(Integer id) {
        return jdbcTemplate.queryForObject(selectByIdSQL, BeanPropertyRowMapper.newInstance(RSVP.class), id);
    }

    public RSVP findByName(String fullname) {
        return jdbcTemplate.queryForObject(selectByNameSQL, BeanPropertyRowMapper.newInstance(RSVP.class), fullname);
    }

    public Boolean save(RSVP rsvp) {
        Integer iResult = jdbcTemplate.update(insertSQL, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(),
                rsvp.getConfirmationDate(), rsvp.getComments());

        return iResult > 0 ? true : false;
    }

    public Boolean update(RSVP rsvp) {
        Integer iResult = 0;

        iResult = jdbcTemplate.update(updateSQL, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(),
                rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getId());

        return iResult > 0 ? true : false;
    }

    public int[] batchUpdate(List<RSVP> rsvps) {

        return jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, rsvps.get(i).getFullName());
                ps.setString(2, rsvps.get(i).getEmail());
                ps.setString(3, rsvps.get(i).getPhone());
                ps.setDate(4, rsvps.get(i).getConfirmationDate());
                ps.setString(5, rsvps.get(i).getComments());
            }

            public int getBatchSize() {
                return rsvps.size();
            }
        });
    }

}
