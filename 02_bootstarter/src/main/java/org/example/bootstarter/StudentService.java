package org.example.bootstarter;

import java.util.ArrayList;

public interface StudentService {
    public ArrayList<Student> printStudents();

    public void createStudent(String firstName, String lastName, int age);

    public void removeStudent(int id);

    public String removeAll();
}
