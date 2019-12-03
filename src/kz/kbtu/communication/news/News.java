package kz.kbtu.communication.news;

import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.base.Employee;
import kz.kbtu.communication.message.Message;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class News extends Message implements Serializable {
    private Faculty faculty;

    public News(Faculty faculty, String title, String text, Employee sender, Date timestamp) {
        super(title, text, sender, timestamp);

        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public final void print() {
        Employee sender = getSender();

        System.out.println(String.format("Title: %s [%s]", getTitle(), faculty));
        System.out.println(String.format("Text: %s", getText()));
        System.out.println(String.format("Sender: %s [%s]", sender.getFullName(), sender.getLogin()));
        System.out.println(String.format("Date: %s", getTimestamp()));
    }

    @Override
    public String toString() {
        return "News{" +
                "faculty=" + faculty +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        if (!super.equals(o)) return false;
        News news = (News) o;
        return faculty == news.faculty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), faculty);
    }
}
