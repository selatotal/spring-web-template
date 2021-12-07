package br.com.selat.template.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PersonEntity findById(Long id) {
        String query = "SELECT * FROM PERSON WHERE ID = ?";
        try {
            return jdbcTemplate.queryForObject(query, PersonEntity::rowMapper, id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public PersonEntity save(PersonEntity entity) {
        String query = "INSERT INTO PERSON (NAME, ADDRESS, BIRTH_DATE, EMAIL, GENDER) VALUES (?, ?, ?, ?, ?) RETURNING ID";
        Long id = jdbcTemplate.queryForObject(query, Long.class, entity.name(), entity.address(), Timestamp.from(entity.birthDate()), entity.email(), entity.gender().name());
        return new PersonEntity(id, entity.name(), entity.address(), entity.birthDate(), entity.email(), entity.gender());
    }

    public boolean update(PersonEntity entity) {
        String query = "UPDATE PERSON SET NAME = ?, ADDRESS = ?, BIRTH_DATE = ?, EMAIL = ?, GENDER = ? WHERE ID = ?";
        return jdbcTemplate.update(query, entity.name(), entity.address(), Timestamp.from(entity.birthDate()), entity.email(), entity.gender().name(), entity.id()) == 1;
    }

    public boolean remove(Long id) {
        String query = "DELETE FROM PERSON WHERE ID = ?";
        return jdbcTemplate.update(query, id) == 1;
    }
}
