/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_test;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {
    private List<Course> courses;

    public Instructor(String username, String password, String name) {
        super(username, password, name);
        this.courses = new ArrayList<>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
