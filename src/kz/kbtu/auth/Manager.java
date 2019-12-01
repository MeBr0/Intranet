package kz.kbtu.auth;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.message.ManagingNews;
import kz.kbtu.message.News;

import java.util.Calendar;
import java.util.Date;

public class Manager extends Employee implements ManagingNews {
    private Faculty faculty;

    public Manager(Faculty faculty, int salary, String login, String firstName, String lastName) {
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
}
