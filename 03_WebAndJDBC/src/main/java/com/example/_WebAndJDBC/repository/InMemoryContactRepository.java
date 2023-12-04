package com.example._WebAndJDBC.repository;

import com.example._WebAndJDBC.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class InMemoryContactRepository implements ContactRepository{

    private final List<Contact> contacts = new ArrayList<>();

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in InMemoryContactRepository");

        return contacts;
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Call findById in InMemoryContactRepository");

        return contacts.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in InMemoryContactRepository");

        contact.setId(System.currentTimeMillis());
        contacts.add(contact);

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in InMemoryContactRepository");

        Contact existedContact = findById(contact.getId()).orElse(null);
        if (existedContact != null) {
            existedContact.setFirstname(contact.getFirstname());
            existedContact.setLastname(contact.getLastname());
            existedContact.setEmail(contact.getEmail());
            existedContact.setPhone(contact.getPhone());
        }

        return existedContact;
    }

    @Override
    public void delete(Long id) {
        log.debug("Call update in InMemoryContactRepository");

        findById(id).ifPresent(contacts::remove);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        this.contacts.addAll(contacts);
    }
}
