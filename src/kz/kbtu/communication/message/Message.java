package kz.kbtu.communication.message;

import kz.kbtu.auth.base.Employee;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Message implements Serializable {
    private String title;
    private String text;
    private Employee sender;
    private Date timestamp;

    public Message(String title, String text, Employee sender, Date timestamp) {
        this.title = title;
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Employee getSender() {
        return sender;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void print() {
        Employee sender = getSender();

        System.out.println(String.format("Title: %s", title));
        System.out.println(String.format("Text: %s", text));
        System.out.println(String.format("Sender: %s [%s]", sender.getFullName(), sender.getLogin()));
        System.out.println(String.format("Date: %s", timestamp));
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", sender=" + sender +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return title.equals(message.title) &&
                text.equals(message.text) &&
                sender.equals(message.sender) &&
                timestamp.equals(message.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, text, sender, timestamp);
    }
}
