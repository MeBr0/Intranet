package kz.kbtu.auth.base;

import kz.kbtu.communication.message.Message;
import kz.kbtu.communication.message.Messaging;

import java.util.*;

public abstract class Employee extends User implements Messaging {
    private int salary;
    private List<Message> messages;

    {
        messages = new ArrayList<>();
    }

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

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public Message readMessage(String title) {
        for (Message message: messages) {
            if (message.getTitle().equals(title)) {
                return message;
            }
        }

        return null;
    }

    @Override
    public void sendMessage(Message message, Messaging target) {
        target.getMessages().add(message);
    }

    @Override
    public Message createMessage(String title, String text) {
        Date timestamp = Calendar.getInstance().getTime();

        return new Message(title, text, this, timestamp);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                "} " + super.toString();
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
