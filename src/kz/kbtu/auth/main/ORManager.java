package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;

public class ORManager extends Employee {

    public ORManager(int salary, String login, String firstName, String lastName) {
        super(salary, login, firstName, lastName);
    }

    @Override
    public String toString() {
        return String.format("ORManager: { %s }", super.toString());
    }
}
