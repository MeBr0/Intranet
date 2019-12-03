package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
import kz.kbtu.communication.news.ManagingNews;
import kz.kbtu.communication.news.News;
import kz.kbtu.communication.order.SendingOrders;
import kz.kbtu.communication.order.Order;
import kz.kbtu.communication.order.OrderStatus;
import kz.kbtu.study.File;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.CourseStatus;
import kz.kbtu.study.course.ManagingCourses;
import kz.kbtu.study.course.MarkMode;
import kz.kbtu.study.throwable.DeadlinePassed;
import kz.kbtu.study.throwable.NotCurrentCourse;

import java.io.Serializable;
import java.util.*;

public class Teacher extends Employee implements ManagingCourses, ManagingNews, SendingOrders, Serializable {
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
    public Order sendOrder(String title, String text, Executor executor) {
        Date timestamp = Calendar.getInstance().getTime();

        Order order = new Order(OrderStatus.NEW, title, text, this, timestamp);

        executor.addOrder(order);

        return order;
    }

    public File createFile(String title, String text) {
        return new File(title, text, getFullName());
    }

    public void putMark(String login, Course course, MarkMode mode, double score)
            throws NotCurrentCourse, DeadlinePassed {

        if (course.getStatus(login) == CourseStatus.CURRENT) {
            course.putMarks(login, mode, score);
        }
        else {
            throw new NotCurrentCourse(login, course);
        }
    }

    public final void print() {
        System.out.println(String.format("Full name: %s [%s]", getFullName(), getLogin()));
        System.out.println(String.format("Birth date: %s", getBirthDate()));
        System.out.println(String.format("Gender: %s", getGender()));
        System.out.println(String.format("Phone number: %s", getPhoneNumber()));
        System.out.println(String.format("Email: %s", getEmail()));
        System.out.println(String.format("Faculty: %s", faculty));
        System.out.println(String.format("Position: %s", position));
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
