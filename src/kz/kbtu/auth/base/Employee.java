package kz.kbtu.auth.base;

import java.util.Date;

public abstract class Employee extends User {
    private int salary;

    public Employee(int salary, String login, String firstName, String lastName) {
        super(login, firstName, lastName);

        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void updateSalary(int delta) {
        this.salary += delta;
    }
}
