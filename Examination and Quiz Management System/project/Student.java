package project_test;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> courses; // New member variable to store enrolled courses

    public Student(String username, String password, String name) {
        super(username, password, name);
        this.courses = new ArrayList<>();
    }

    public List<Course> getCourses() {
        return courses;
    }
}
