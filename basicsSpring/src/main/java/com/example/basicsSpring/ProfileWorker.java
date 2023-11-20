package com.example.basicsSpring;

import org.springframework.stereotype.Component;

@Component
public class ProfileWorker {
    private final ContactsApp contactsApp;
    private final InitialData initialData;

    public ProfileWorker(ContactsApp contactsApp, InitialData initialData) {
        this.contactsApp = contactsApp;
        this.initialData = initialData;
    }

    public void work() {
        initialData.initData();
        contactsApp.doWork();
    }
}
