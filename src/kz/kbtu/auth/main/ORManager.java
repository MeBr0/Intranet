package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.communication.order.Order;
import kz.kbtu.communication.order.OrderStatus;
import kz.kbtu.communication.order.SendingOrders;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.CourseStatus;
import kz.kbtu.study.throwable.CreditOverflow;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ORManager extends Employee implements SendingOrders, Serializable {

    public ORManager(int salary, String login, String firstName, String lastName) {
        super(salary, login, firstName, lastName);
    }

    public Course createCourse(String name, int creditNumber, Teacher teacher) {
        Course course = new Course(name, creditNumber, teacher);

        teacher.addCourse(course);

        return course;
    }

    public void offerCourse(Course course, List<Student> students) throws CreditOverflow {
        for (Student student: students) {

            course.addStudent(student);
            course.updateStatus(student.getLogin(), CourseStatus.FUTURE);

            student.addCourse(course);
        }
    }

    @Override
    public Order sendOrder(String title, String text, Executor executor) {
        Date timestamp = Calendar.getInstance().getTime();

        Order order = new Order(OrderStatus.NEW, title, text, this, timestamp);

        executor.addOrder(order);

        return order;
    }

    @Override
    public String toString() {
        return String.format("ORManager: { %s }", super.toString());
    }
}
