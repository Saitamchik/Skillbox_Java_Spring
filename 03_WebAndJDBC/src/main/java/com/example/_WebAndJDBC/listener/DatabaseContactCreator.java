package com.example._WebAndJDBC.listener;

import com.example._WebAndJDBC.Contact;
import com.example._WebAndJDBC.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactCreator {

    private final ContactService contactService;

    //@EventListener(ApplicationStartedEvent.class)
    public void createContactData() {
        log.debug("Calling DatabaseContactCreator->createContactData...");

        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int value = i + 1;
            Contact contact = new Contact();
            contact.setId((long) value);
            contact.setFirstname("Firstname " + value);
            contact.setLastname("Lastname " + value);
            contact.setEmail("Email " + value);
            contact.setPhone("Phone " + value);

            contacts.add(contact);
        }

        contactService.batchInsert(contacts);
    }
}
