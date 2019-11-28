package kz.kbtu.user;

import kz.kbtu.user.base.Gender;
import kz.kbtu.user.base.User;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private final String id;
    private int yearOfStudy;
    private List<String> courses;
    private double gpa;
    private Faculty faculty;
    private Degree degree;

    private static int count = 0;

    {
        courses = new ArrayList<>();
        gpa = 0;
    }

    public Student(Faculty faculty, Degree degree, String login, String firstName, String lastName) {
        super(login, firstName, lastName);

        id = "";    // TODO: id generator
        this.faculty = faculty;
        this.degree = degree;

        count++;
    }

    public String getId() {
        return id;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void incrementYearOfStudy() {
        this.yearOfStudy++;
    }

    public double getGpa() {
        return gpa;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }
}
