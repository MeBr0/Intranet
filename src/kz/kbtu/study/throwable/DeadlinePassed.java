package kz.kbtu.study.throwable;

public class DeadlinePassed extends Exception {

    public DeadlinePassed(String reason) {
        super(reason);
    }
}
