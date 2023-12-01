package org.example.bootstarter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@ShellComponent
public class StudentServiceImpl implements StudentService{

    @Value("${init.file.url}")
    private String inputFileUrl;

    @Value("${event.init.switch}")
    private boolean eventInit;

    private EventQueue eventQueue = new EventQueue();
    private EventQueueWorker eventQueueWorker = new EventQueueWorker(eventQueue);
    private static ArrayList<Student> students = new ArrayList<>();
    private int nextId = 1;

    @ShellMethod(key = "print")
    @Override
    public ArrayList<Student> printStudents() {
        return new ArrayList<>(students);
    }


    @ShellMethod(key = "create")
    @Override
    public void createStudent(String firstName, String lastName, int age) {
        Student student = new Student(nextId++, firstName, lastName, age);
        students.add(student);

        eventQueueWorker.startEventQueue(" Create " + student.toString());
    }

    @ShellMethod(key = "remove")
    @Override
    public void removeStudent(int id) {
        if (students.size() != 0) {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == id) {
                    students.remove(i);
                    eventQueueWorker.startEventQueue(" Remove student id = " + id);
                } else {
                    eventQueueWorker.startEventQueue(" Student not found");
                }
            }
        } else {
            eventQueueWorker.startEventQueue(" Students list is null");
        }
    }

    @ShellMethod(key = "remove-all")
    @Override
    public String removeAll() {
        for (int i = 0; i < students.size(); i++) {
                students.remove(i);
        }
        return "Students remove";
    }


    @Profile("init")
    @Bean
    public void initProfile() {
        System.out.println("Profiles: init \nThe source data is initialised");
        try (FileReader reader = new FileReader(inputFileUrl)) {
            BufferedReader readerLine = new BufferedReader(reader);

            String[] studentData = readerLine.readLine().split(";");
            String firstName;
            String lastName;
            int age;

            while (studentData != null) {
                firstName = studentData[0].trim();
                lastName = studentData[1].trim();
                age = Integer.parseInt(studentData[2].trim());

                Student student = new Student(nextId++, firstName, lastName, age);
                students.add(student);
                if (eventInit) {
                    eventQueueWorker.startEventQueue(" Create " + student.toString());
                }
                studentData = readerLine.readLine().split(";");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Profile("default")
    @Bean
    public void defaultProfile() {
        System.out.println("Profiles: Default \nThe source data is not initialised");
    }
}
