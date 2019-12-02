package kz.kbtu.auth.base;

import kz.kbtu.communication.message.Message;
import kz.kbtu.communication.message.Messaging;

import java.io.Serializable;
import java.util.*;

public abstract class Employee extends User implements Messaging, Serializable {
    private int salary;
    private List<Message> messages;

    {
        messages = new ArrayList<>();
    }

    protected Employee(int salary, String login, String firstName, String lastName) {
        super(login, firstName, lastName);

        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void updateSalary(int delta) {
        this.salary += delta;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void putMessage(Message message) {
        this.messages.add(message);
    }

    @Override
    public void sendMessage(String title, String text, Messaging target) {
        Date timestamp = Calendar.getInstance().getTime();

        Message message = new Message(title, text, this, timestamp);
        target.putMessage(message);
    }

    @Override
    public String toString() {
        return String.format("salary: %d, %s", salary, super.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return salary == employee.salary;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salary);
    }
}
