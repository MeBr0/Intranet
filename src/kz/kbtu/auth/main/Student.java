package kz.kbtu.auth.main;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.CourseStatus;
import kz.kbtu.study.course.ManagingCourses;
import kz.kbtu.study.throwable.CreditOverflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student extends User implements ManagingCourses, Serializable {

    private final String id;
    private int yearOfStudy;
    private List<Course> courses;
    private double gpa;
    private Faculty faculty;
    private Degree degree;

    private final int CREDIT_LIMIT = 21;

    private static int count = 0;

    {
        courses = new ArrayList<>();
        gpa = 0;
    }

    Student(Faculty faculty, Degree degree, String login, String firstName, String lastName) {
        super(login, firstName, lastName);

        id = "";    // TODO: id generator
        this.faculty = faculty;
        this.degree = degree;
        incrementYearOfStudy();

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
    public List<Course> getCourses() {
        return this.courses;
    }

    @Override
    public void addCourse(Course course) throws CreditOverflow {
        if (course.getStatuses().get(getLogin()) == CourseStatus.CURRENT) {
            int currentCredits = 0;

            for (Course course1 : courses) {
                if (course1.getStatuses().get(getLogin()) == CourseStatus.CURRENT) {
                    currentCredits += course1.getCreditNumber();
                }
            }

            if (currentCredits + course.getCreditNumber() <= CREDIT_LIMIT) {
                this.courses.add(course);
            }
            else {
                throw new CreditOverflow(course, CREDIT_LIMIT);
            }
        }
        else {
            this.courses.add(course);
        }
    }

    @Override
    public boolean removeCourse(String name) {
        for (Course course: this.courses) {
            if (course.getName().equals(name)) {
                courses.remove(course);

                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Student: { id: %s, yearOfStudy: %d, gpa: %.2f, faculty: %s, degree: %s, %s }",
                id, yearOfStudy, gpa, faculty, degree, super.toString());
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
