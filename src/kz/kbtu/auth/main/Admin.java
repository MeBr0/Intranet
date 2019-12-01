package kz.kbtu.auth.main;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;

import java.io.Serializable;

public class Admin extends User implements Serializable {

    private Admin(String login, String firstName, String lastName) {
        super(login, firstName, lastName);
    }

    public static Admin createAdmin(String login, String firstName, String lastName) {
        return new Admin(login, firstName, lastName);
    }

    public Student createStudent(Faculty faculty, Degree degree, String login, String firstName, String lastName) {
        return new Student(faculty, degree, login, firstName, lastName);
    }

    public Manager createManager(Faculty faculty, int salary, String login, String firstName, String lastName) {
        return new Manager(faculty, salary, login, firstName, lastName);
    }

    public ORManager createOrManager(int salary, String login, String firstName, String lastName) {
        return new ORManager(salary, login, firstName, lastName);
    }

    public Executor createExecutor(int salary, String login, String firstName, String lastName) {
        return new Executor(salary, login, firstName, lastName);
    }

    public Teacher createTeacher(Faculty faculty, TeacherPosition position, int salary, String login, String firstName,
                                 String lastName) {
        return new Teacher(faculty, position, salary, login, firstName, lastName);
    }
}
