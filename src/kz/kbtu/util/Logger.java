package kz.kbtu.util;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.Admin;
import kz.kbtu.auth.main.Manager;
import kz.kbtu.auth.main.ORManager;
import kz.kbtu.auth.main.Teacher;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.communication.news.News;
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
            writer.write(FORMAT.format(LocalDateTime.now()) + " - Admin " + admin.getLogin() +
                    " entered to system!\n");

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in enterAdmin()");
        }
    }

    public void createUser(Admin admin, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(FORMAT.format(LocalDateTime.now()) + " - Admin " + admin.getLogin() +
                    " created " + user.getClass().getSimpleName() + " " + user.getLogin() +  "\n");

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in createUser()");
        }
    }

    public void removeUser(Admin admin, User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(FORMAT.format(LocalDateTime.now()) + " - Admin " + admin.getLogin() +
                    " removed " + user.getClass().getSimpleName() + " " + user.getLogin() +  "\n");

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in removeUser()");
        }
    }

    public void createCourse(ORManager manager, Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(FORMAT.format(LocalDateTime.now()) + " - ORManager " + manager.getLogin() +
                    " created Course " + course.getName() +  "\n");

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in createCourse()");
        }
    }

    public void removeCourse(ORManager manager, Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(FORMAT.format(LocalDateTime.now()) + " - ORManager " + manager.getLogin() +
                    " removed Course " + course.getName() +  "\n");

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in removeUser()");
        }
    }

    public void offerCourse(ORManager manager, Course course, int yearOfStudy, Faculty faculty, Degree degree) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(FORMAT.format(LocalDateTime.now()) + " - ORManager " + manager.getLogin() +
                    " offered Course " + course.getName() + " offered to " + yearOfStudy + " year of study, " +
                    faculty + " faculty, " + degree + " degree students" + "\n");

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in createCourse()");
        }
    }

    public void uploadFile(Teacher teacher, Course course, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(FORMAT.format(LocalDateTime.now()) + " - Teacher " + teacher.getLogin() +
                    " added File " + file.getTitle() + " to Course " + course.getName() + "\n");

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in uploadFile()");
        }
    }

    public void writeNews(Manager manager, News news) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG, true))) {
            writer.write(FORMAT.format(LocalDateTime.now()) + " - Manager " + manager.getLogin() +
                    " added News " + news.getTitle() + "\n");

            writer.flush();
        }
        catch (IOException e) {
            System.err.println("Cannot write in writeNews()");
        }
    }
}
