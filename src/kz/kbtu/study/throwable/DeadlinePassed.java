package kz.kbtu.study.throwable;

import java.util.Date;

public class DeadlinePassed extends Exception {

    public DeadlinePassed(Date deadline) {
        super(String.format("It is too late for %s", deadline.toString()));
    }
}
