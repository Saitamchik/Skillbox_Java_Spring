package com.example._WebAndJDBC.repository;

import com.example._WebAndJDBC.Contact;
import com.example._WebAndJDBC.repository.Mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in DatabaseContactRepository");

        String sql = "SELECT * FROM contacts";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Call findById in DatabaseContactRepository");

        String sql = "SELECT * FROM contacts WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );

        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in DatabaseContactRepository");

        contact.setId(System.currentTimeMillis());

        String sql = "INSERT INTO contacts (id, firstname, lastname, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getId(), contact.getFirstname(), contact.getLastname(), contact.getEmail(), contact.getPhone());

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in DatabaseContactRepository");

        Contact existedContact = findById(contact.getId()).orElse(null);
        if (existedContact != null) {
            String sql = "UPDATE contacts SET firstname = ?, lastname = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstname(), contact.getLastname(), contact.getEmail(), contact.getPhone(), contact.getId());
            return contact;
        }

        log.warn("Contact not found");

        return null;
    }

    @Override
    public void delete(Long id) {
        log.debug("Call delete in DatabaseContactRepository");

        String sql = "DELETE FROM contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("Call batchInsert in DatabaseContactRepository");

        String sql = "INSERT INTO contacts (id, firstname, lastname, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Contact contact = contacts.get(i);
                ps.setLong(1, contact.getId());
                ps.setString(2, contact.getFirstname());
                ps.setString(3, contact.getLastname());
                ps.setString(4, contact.getEmail());
                ps.setString(5, contact.getPhone());
            }

            @Override
            public int getBatchSize() {
                return contacts.size();
            }
        });
    }
}
