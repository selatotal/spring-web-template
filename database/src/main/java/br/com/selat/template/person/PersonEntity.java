package br.com.selat.template.person;

import br.com.selat.template.contract.v1.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public record PersonEntity(
        Long id,
        String name,
        String address,
        Instant birthDate,
        String email,
        Gender gender
){

    public static PersonEntity rowMapper(ResultSet rs, int i) throws SQLException {
        return new PersonEntity(rs.getLong("ID"),
                rs.getString("NAME"),
                rs.getString("ADDRESS"),
                rs.getTimestamp("BIRTH_DATE").toInstant(),
                rs.getString("EMAIL"),
                Gender.valueOf(rs.getString("GENDER"))
                );
    }
}