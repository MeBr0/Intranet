package kz.kbtu.util;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
import kz.kbtu.communication.message.Message;
import kz.kbtu.communication.news.News;
import kz.kbtu.communication.order.Order;
import kz.kbtu.study.File;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.MarkMode;

import java.util.List;

public class Printer {

    private static Printer printer = null;

    private Printer() {

    }

    public static Printer getInstance() {
        if (printer == null) {
            printer = new Printer();
        }

        return printer;
    }

    private final char INFO = '-';
    private final char RESULT = '!';
    private final char ERROR = '!';

    public void printInfo(String info) {
        System.out.println(INFO + " " + info);
    }

    public void printResult(String result) {
        System.out.println(RESULT + " " + result);
    }

    public void printError(String error) {
        System.err.println(ERROR + " " + error);
    }

    public void printOptions(String[] options) {
        for (int i = 0; i < options.length; ++i) {
            System.out.println(i+1 + ". " + options[i]);
        }
    }

    public void printFaculties(Faculty[] faculties) {
        for (int i = 0; i < faculties.length; ++i) {
            System.out.println(i+1 + ". " + faculties[i]);
        }
    }

    public void printDegrees(Degree[] degrees) {
        for (int i = 0; i < degrees.length; ++i) {
            System.out.println(i+1 + ". " + degrees[i]);
        }
    }

    public void printPositions(TeacherPosition[] positions) {
        for (int i = 0; i < positions.length; ++i) {
            System.out.println(i+1 + ". " + positions[i]);
        }
    }

    public void printModes(MarkMode[] modes) {
        for (int i = 0; i < modes.length; ++i) {
            System.out.println(i+1 + ". " + modes[i]);
        }
    }

    public void printUsers(List<User> users) {
        for (int i = 0; i < users.size(); ++i) {
            User user = users.get(i);

            System.out.println(String.format("%d. %s [%s]", i+1, user.getFullName(), user.getLogin()));
        }
    }

    public void printCourses(List<Course> courses) {
        for (int i = 0; i < courses.size(); ++i) {
            System.out.println(i+1 + ". " + courses.get(i).getName());
        }
    }

    public void printOrders(List<Order> orders) {
        for (int i = 0; i < orders.size(); ++i) {
            System.out.println(i+1 + ". " + orders.get(i));
        }
    }

    public void printFiles(List<File> files) {
        for (int i = 0; i < files.size(); ++i) {
            System.out.println(i+1 + ". " + files.get(i).getTitle());
        }
    }

    public void printMessages(List<Message> messages) {
        for (int i = 0; i < messages.size(); ++i) {
            System.out.println(i+1 + ". " + messages.get(i).getTitle());
        }
    }

    public void printNewses(List<News> newses) {
        for (int i = 0; i < newses.size(); ++i) {
            System.out.println(i+1 + ": " + newses.get(i).getTitle());
        }
    }

}
