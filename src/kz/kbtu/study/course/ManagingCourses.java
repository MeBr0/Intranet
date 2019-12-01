package kz.kbtu.study.course;

import kz.kbtu.study.throwable.CreditOverflow;

import java.util.List;

public interface ManagingCourses {
    List<Course> getCourses();
    void addCourse(Course course) throws CreditOverflow;
    boolean removeCourse(String name);
}
