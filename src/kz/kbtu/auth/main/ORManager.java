package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.study.course.Course;

public class ORManager extends Employee {

    public ORManager(int salary, String login, String firstName, String lastName) {
        super(salary, login, firstName, lastName);
    }

    public Course createCourse(String name, int creditNumber, Teacher teacher) {
        Course course = new Course(name, creditNumber, teacher);

        teacher.addCourse(course);

        return course;
    }

    @Override
    public String toString() {
        return String.format("ORManager: { %s }", super.toString());
    }
}
