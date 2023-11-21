package com.example.basicsSpring;

import org.springframework.beans.factory.annotation.Value;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactsAppDefault implements ContactsApp {

    @Value("${save.file.url}")
    private String saveFileUrl;

    private final String REGEX_PHONE = "[+8]+[0-9]{8}+";
    private final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";

    private static ArrayList<Contacts> contacts = new ArrayList<>();

    private void printContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Contacts is null");
        } else {
            for (Contacts contact : contacts) {
                System.out.println(contact.toString());
            }
        }
    }

    private void addContact(Scanner scanner) {
        System.out.println("Enter your full name, telephone number and email address separated by semicolons (;):");
        String input = scanner.nextLine();

        String[] contactData = input.split(";");
        String[] contactFIO = contactData[0].trim().split(" ");
        String phoneNumber = contactData[1].trim();
        String email = contactData[2].trim();
        Pattern patternPhone = Pattern.compile(REGEX_PHONE);
        Pattern patternEmail = Pattern.compile(REGEX_EMAIL);
        Matcher matcherPhone = patternPhone.matcher(phoneNumber);
        Matcher matcherEmail = patternEmail.matcher(email);

        if (contactData.length != 3) {
            System.out.println("Incorrect entry, try again");
            return;
        } else if (contactFIO.length != 3) {
            System.out.println("Incorrect name, try again");
            return;
        } else if (matcherPhone.matches() == false) {
            System.out.println("Incorrect phone number, try again ");
            return;
        } else if (matcherEmail.matches() == false) {
            System.out.println("Incorrect email, try again ");
            return;
        }

        String fullName = contactData[0].trim();

        Contacts newContact = new Contacts(fullName, phoneNumber, email);
        contacts.add(newContact);

        System.out.println("Contact has been successfully added");
    }

    private void removeContact(Scanner scanner) {
        System.out.println("Enter the email address to be deleted:");
        String email = scanner.nextLine();

        boolean removed = false;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getEmail().equals(email)) {
                contacts.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Contact successfully deleted");
        } else {
            System.out.println("No contact with the entered email address was found");
        }
    }

    private void save() {
        try (FileWriter writer = new FileWriter(saveFileUrl, false)) {
            if (contacts.isEmpty()) {
                System.out.println("Contacts is null");
            } else {
                for (Contacts contact : contacts) {
                    writer.write(contact.toString());
                    writer.append("\n");
                    System.out.println(contact.toString());
                }
                writer.flush();
                writer.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void initData(ArrayList<Contacts> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("Init file is null");
        } else {
            this.contacts.addAll(contacts);
        }
    }

    @Override
    public void doWork() {
        Scanner scanner = new Scanner(System.in);
        int option;

        while (true) {
            option = 0;
            System.out.println("\n1. Get all contact");
            System.out.println("2. Add contact");
            System.out.println("3. Remove contact");
            System.out.println("4. Save changes");
            System.out.println("5. Exit");
            System.out.print("Select the option: ");
            try {
                option = scanner.nextInt();
            } catch (Exception ex) {
                System.out.println("You entered the wrong number");
                scanner.next();
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println("Get all contacts");
                    printContacts();
                    break;
                case 2:
                    System.out.println("Add contact");
                    scanner.nextLine();
                    addContact(scanner);
                    break;
                case 3:
                    System.out.println("Remove contact");
                    scanner.nextLine();
                    removeContact(scanner);
                    break;
                case 4:
                    System.out.println("Save changes");
                    save();
                    break;
                case 5:
                    System.out.println("Exit");
                    System.exit(0);
                    break;
                default:
                    System.out.println("The command you specified does not exist");
                    break;
            }
        }
    }
}
