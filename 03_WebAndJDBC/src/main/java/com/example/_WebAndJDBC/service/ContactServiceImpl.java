package com.example._WebAndJDBC.service;

import com.example._WebAndJDBC.Contact;
import com.example._WebAndJDBC.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService{

    private final ContactRepository contactRepository;

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in InMemoryContactRepository");

        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        log.debug("Call findById in InMemoryContactRepository");

        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in InMemoryContactRepository");

        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in InMemoryContactRepository");

        return contactRepository.update(contact);
    }

    @Override
    public void delete(Long id) {
        log.debug("Call delete in InMemoryContactRepository");

        contactRepository.delete(id);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("Call batchInsert in InMemoryContactRepository");

        contactRepository.batchInsert(contacts);
    }
}
