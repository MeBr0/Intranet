package kz.kbtu.study.throwable;

import kz.kbtu.study.course.Course;

public class NotCurrentCourse extends Exception {
    public NotCurrentCourse(String login, Course course) {
        super(String.format("Student with login %s not taking course %s", login, course.getName()));
    }
}
