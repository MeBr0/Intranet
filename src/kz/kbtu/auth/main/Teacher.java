package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
import kz.kbtu.communication.news.ManagingNews;
import kz.kbtu.communication.news.News;
import kz.kbtu.study.File;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.ManagingCourses;

import java.io.Serializable;
import java.util.*;

public class Teacher extends Employee implements ManagingCourses, ManagingNews, Serializable {
    private Faculty faculty;
    private TeacherPosition position;
    private List<Course> courses;

    {
        courses = new ArrayList<>();
    }

    Teacher(Faculty faculty, TeacherPosition position, int salary, String login, String firstName,
                   String lastName) {
        super(salary, login, firstName, lastName);

        this.faculty = faculty;
        this.position = position;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public TeacherPosition getPosition() {
        return position;
    }

    public void setPosition(TeacherPosition position) {
        this.position = position;
    }

    @Override
    public News createNews(String title, String text) {
        Date timestamp = Calendar.getInstance().getTime();

        return new News(this.faculty ,title, text, this, timestamp);
    }

    @Override
    public List<Course> getCourses() {
        return this.courses;
    }

    @Override
    public void addCourse(Course course) {
        this.courses.add(course);
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

    public File createFile(String title, String text) {
        return new File(title, text, getFullName());
    }

    @Override
    public String toString() {
        return String.format("Teacher { faculty: %s, position: %s, %s }", faculty, position, super.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return faculty == teacher.faculty &&
                position == teacher.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(faculty, position);
    }
}
