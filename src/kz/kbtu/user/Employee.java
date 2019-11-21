package kz.kbtu.user;

import java.util.Date;

public abstract class Employee extends User {
    private int salary;

    public Employee(int salary, String login, String password, String firstName, String lastName, Date birthDate,
                    Gender gender) {
        super(login, password, firstName, lastName, birthDate, gender);

        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void updateSalary(int delta) {
        this.salary += delta;
    }
}
