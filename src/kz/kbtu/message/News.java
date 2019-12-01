package kz.kbtu.message;

import kz.kbtu.auth.Faculty;
import kz.kbtu.auth.base.Employee;

import java.util.Date;
import java.util.Objects;

public class News extends Message {
    private Faculty faculty;

    public News(String title, String text, Employee sender, Date timestamp, Faculty faculty) {
        super(title, text, sender, timestamp);

        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
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
