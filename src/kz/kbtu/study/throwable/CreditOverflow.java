package kz.kbtu.study.throwable;

import kz.kbtu.study.Course;

public class CreditOverflow extends Exception {

    public CreditOverflow(Course course, int limit) {
        super(String.format("Cannot add course %s with %d credits, limit is %d",
                course.getName(), course.getCreditNumber(), limit));
    }
}
