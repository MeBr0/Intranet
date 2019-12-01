package kz.kbtu.auth;

import kz.kbtu.auth.base.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                ", courses=" + courses +
                ", gpa=" + gpa +
                ", faculty=" + faculty +
                ", degree=" + degree +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return yearOfStudy == student.yearOfStudy &&
                Double.compare(student.gpa, gpa) == 0 &&
                id.equals(student.id) &&
                faculty == student.faculty &&
                degree == student.degree;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, yearOfStudy, gpa, faculty, degree);
    }
}
