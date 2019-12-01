package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.communication.news.ManagingNews;
import kz.kbtu.communication.news.News;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Manager extends Employee implements ManagingNews, Serializable {
    private Faculty faculty;

    Manager(Faculty faculty, int salary, String login, String firstName, String lastName) {
        super(salary, login, firstName, lastName);

        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public News createNews(String title, String text) {
        Date timestamp = Calendar.getInstance().getTime();

        return new News(this.faculty ,title, text, this, timestamp);
    }

    @Override
    public String toString() {
//        return "Manager{" +
//                "faculty=" + faculty +
//                "} " + super.toString();

        return String.format("Manager { faculty: %s, %s}", faculty, super.toString());
    }
}
