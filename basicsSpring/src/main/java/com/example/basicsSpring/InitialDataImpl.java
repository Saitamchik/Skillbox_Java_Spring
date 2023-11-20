package com.example.basicsSpring;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InitialDataImpl implements InitialData {
    private static ArrayList<Contacts> contacts = new ArrayList<>();
    @Value("${init.file.url}")
    private String inputFileUrl;

    @Override
    public void initData() {
        System.out.println("Profiles: init \nThe source data is initialised");
        try (FileReader reader = new FileReader(inputFileUrl)) {
            BufferedReader readerLine = new BufferedReader(reader);
            String[] contactData = readerLine.readLine().split(";");
            String fullName;
            String phoneNumber;
            String email;
            while (contactData != null) {
                fullName = contactData[0].trim();
                phoneNumber = contactData[1].trim();
                email = contactData[2].trim();
                Contacts newContact = new Contacts(fullName, phoneNumber, email);
                contacts.add(newContact);
                contactData = readerLine.readLine().split(";");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

        ContactsAppDefault contactsAppDefault = new ContactsAppDefault();
        contactsAppDefault.initData(contacts);
    }
}
