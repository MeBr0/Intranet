package kz.kbtu.util;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.*;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.communication.news.ManagingNews;
import kz.kbtu.communication.news.News;
import kz.kbtu.communication.order.Order;
import kz.kbtu.communication.order.SendingOrders;
import kz.kbtu.study.File;
import kz.kbtu.study.course.Course;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger logger = null;

    private Logger() {

    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }

        return logger;
    }

    private final String LOG = "log.txt";
    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");

    public void enterAdmin(Admin admin) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - Admin %s [%s] entered into system!\n",
                    FORMAT.format(LocalDateTime.now()), admin.getFullName(), admin.getLogin()));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in enterAdmin()");
        }
    }

    public void createUser(Admin admin, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - Admin %s [%s] created %s %s [%s]\n",
                    FORMAT.format(LocalDateTime.now()), admin.getFullName(), admin.getLogin(),
                    user.getClass().getSimpleName(), user.getFullName(), user.getLogin()));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in createUser()");
        }
    }

    public void removeUser(Admin admin, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - Admin %s [%s] removed %s %s [%s]\n",
                    FORMAT.format(LocalDateTime.now()), admin.getFullName(), admin.getLogin(),
                    user.getClass().getSimpleName(), user.getFullName(), user.getLogin()));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in removeUser()");
        }
    }

    public void createCourse(ORManager manager, Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - ORManager %s [%s] created Course %s\n",
                    FORMAT.format(LocalDateTime.now()), manager.getFullName(), manager.getLogin(), course.getName()));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in createCourse()");
        }
    }

    public void removeCourse(ORManager manager, Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - ORManager %s [%s] removed Course %s\n",
                    FORMAT.format(LocalDateTime.now()), manager.getFullName(), manager.getLogin(), course.getName()));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in removeUser()");
        }
    }

    public void offerCourse(ORManager manager, Course course, int yearOfStudy, Faculty faculty, Degree degree) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - ORManager %s [%s] offered Course %s to %s year of study, " +
                            "%s faculty and %s degree students\n",
                    FORMAT.format(LocalDateTime.now()), manager.getFullName(), manager.getLogin(), course.getName(),
                    yearOfStudy, faculty, degree));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in createCourse()");
        }
    }

    public void uploadFile(Teacher teacher, Course course, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - Teacher %s [%s] added File %s in Course %s\n",
                    FORMAT.format(LocalDateTime.now()), teacher.getFullName(), teacher.getLogin(), file.getTitle(),
                    course.getName()));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in uploadFile()");
        }
    }

    public void sendOrder(SendingOrders sender, Order order, Executor executor) {
        User user = (User) sender;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - %s %s [%s] send Order %s to Executor %s [%s]\n",
                    FORMAT.format(LocalDateTime.now()), user.getClass().getSimpleName(), user.getFullName(),
                    user.getLogin(), order.getTitle(), executor.getFullName(), executor.getLogin()));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in sendOrder()");
        }
    }

    public void writeNews(ManagingNews sender, News news) {
        User user = (User) sender;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(String.format("%s - %s %s [%s] wrote News %s\n",
                    FORMAT.format(LocalDateTime.now()), user.getClass().getSimpleName(), user.getFullName(),
                    user.getLogin(), news.getTitle()));

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in writeNews()");
        }
    }
}
