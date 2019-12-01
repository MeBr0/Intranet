package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.CourseStatus;
import kz.kbtu.study.throwable.CreditOverflow;

import java.util.List;

public class ORManager extends Employee {

    public ORManager(int salary, String login, String firstName, String lastName) {
        super(salary, login, firstName, lastName);
    }

    public Course createCourse(String name, int creditNumber, Teacher teacher) {
        Course course = new Course(name, creditNumber, teacher);

        teacher.addCourse(course);

        return course;
    }

    public void offerCourse(Course course, List<Student> students) {
        try {
            for (Student student: students) {

//                System.out.println(student);
                course.addStudent(student);
                course.updateStatus(student.getLogin(), CourseStatus.FUTURE);

                student.addCourse(course);
            }
        }
        catch (CreditOverflow e) {
            System.err.println("Cannot offer course!");
        }
    }

    @Override
    public String toString() {
        return String.format("ORManager: { %s }", super.toString());
    }
}
