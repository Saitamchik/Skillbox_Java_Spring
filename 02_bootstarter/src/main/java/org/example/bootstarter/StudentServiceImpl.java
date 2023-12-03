package org.example.bootstarter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@ShellComponent
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    EventWorker eventWorker = new EventWorker();

    @Value("${init.file.url}")
    private String inputFileUrl;

    @Value("${event.init.switch}")
    private boolean eventInit;

    private final ApplicationEventPublisher eventPublisher;

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

        eventPublisher.publishEvent(new StudentAddedEvent(this, student));
    }

    @ShellMethod(key = "remove")
    @Override
    public void removeStudent(int id) {
        if (students.size() != 0) {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == id) {
                    eventPublisher.publishEvent(new StudentRemoveEvent(this, students.get(i).getId()));
                    students.remove(i);
                    break;
                } else if ((i + 1) == students.size()) {
                    System.out.println(" Student not found");
                }
            }
        } else {
            System.out.println(" Students list is null");
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
                    eventWorker.studentAddedEventPrint(new StudentAddedEvent(this, student));
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
