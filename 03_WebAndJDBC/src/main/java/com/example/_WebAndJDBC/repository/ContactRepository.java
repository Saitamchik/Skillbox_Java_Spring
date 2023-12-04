package com.example._WebAndJDBC.repository;

import com.example._WebAndJDBC.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    List<Contact> findAll();

    Optional<Contact> findById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void delete(Long id);

    void batchInsert(List<Contact> contacts);
}
