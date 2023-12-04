package com.example._WebAndJDBC.service;

import com.example._WebAndJDBC.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    List<Contact> findAll();

    Contact findById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void delete(Long id);

    void batchInsert(List<Contact> contacts);
}
