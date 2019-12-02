package kz.kbtu;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.Admin;
import kz.kbtu.auth.main.Student;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
import kz.kbtu.communication.news.News;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.MarkMode;

import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class Database {

    private static Database database = null;

    private Database() {

    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    public final static Date DEADLINE1 = new GregorianCalendar(2019, Calendar.OCTOBER, 14).getTime();
    public final static Date DEADLINE2 = new GregorianCalendar(2019, Calendar.DECEMBER, 14).getTime();
    public final static Date DEADLINE3 = new GregorianCalendar(2020, Calendar.JANUARY, 10).getTime();

    private List<User> users;
    private List<Course> courses;
    private List<News> news;

    private final String USER = "user.out";
    private final String COURSE = "course.out";
    private final String NEWS = "news.out";

    {
        load();

//        Admin admin = Admin.createAdmin("admin", "", "");
//        users.add(admin);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Course getCourse(String name) {
        for (Course course: courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }

        return null;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public Course removeCourse(String name) {
        for (Course course: courses) {
            if (course.getName().equals(name)) {
                courses.remove(course);

                return course;
            }
        }

        return null;
    }

    public List<News> getNews() {
        return news;
    }

    public void addNews(News news) {
        this.news.add(news);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public User removeUser(String login) {
        for (User user: users) {
            if (user.getLogin().equals(login)) {
                users.remove(user);

                return user;
            }
        }

        return null;
    }

    public User getUser(String login) {
        for (User user: users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }

        return null;
    }

    public User getUser(String login, String password) {
        for (User user: users) {
            if (user.checkCredentials(login, password)) {
                return user;
            }
        }

        return null;
    }

    public <T> List<User> getUsers(Class<T> clazz) {
        List<User> users = new ArrayList<>();

        for (User user: this.users) {
            if (user.getClass() == clazz) {
                users.add(user);
            }
        }

        return users;
    }

    public List<Student> getStudents(int yearOfStudy, Faculty faculty, Degree degree) {
        List<Student> students = new ArrayList<>();

        for (User user: users) {
            if (user instanceof Student) {
                Student student = (Student) user;

                if (student.getFaculty() == faculty && student.getDegree() == degree &&
                        student.getYearOfStudy() == yearOfStudy) {
                    students.add(student);
                }
            }
        }

        return students;
    }

    public Faculty[] getFaculties() {
        return new Faculty[] {Faculty.FIT, Faculty.BS, Faculty.FGA, Faculty.FOGI, Faculty.ISE, Faculty.MCM, Faculty.KMA};
    }

    public Degree[] getDegrees() {
        return new Degree[] {Degree.BACHELOR, Degree.MASTER, Degree.PHILOSOPHY_DOCTOR};
    }

    public MarkMode[] getMarkModes() {
        return new MarkMode[] {MarkMode.ATTESTATION1, MarkMode.ATTESTATION2, MarkMode.FINAL};
    }

    public TeacherPosition[] getPositions() {
        return new TeacherPosition[] {TeacherPosition.TUTOR, TeacherPosition.LECTURER, TeacherPosition.PROFESSOR,
                TeacherPosition.SENIOR_LECTURER};
    }
    public void load() {
        loadUsers();
        loadCourses();
        loadNews();
    }

    public void save() {
        saveUsers();
        saveCourses();
        saveNews();
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER))) {
            users = (ArrayList<User>) ois.readObject();
        }
        catch (ClassNotFoundException e) {
            users = new ArrayList<>();
            System.out.println(USER + ": ClassNotFoundException");
        }
        catch (IOException e) {
            users = new ArrayList<>();
            System.out.println(USER + ": IOException");
        }
    }

    private void loadCourses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COURSE))) {
            courses = (ArrayList<Course>) ois.readObject();
        }
        catch (ClassNotFoundException e) {
            courses = new ArrayList<>();
            System.out.println(COURSE + ": ClassNotFoundException");
        }
        catch (IOException e) {
            courses = new ArrayList<>();
            System.out.println(COURSE + ": IOException");
        }
    }

    private void loadNews() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NEWS))) {
            news = (ArrayList<News>) ois.readObject();
        }
        catch (ClassNotFoundException e) {
            news = new ArrayList<>();
            System.out.println(NEWS + ": ClassNotFoundException");
        }
        catch (IOException e) {
            news = new ArrayList<>();
            System.out.println(NEWS + ": IOException");
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oot = new ObjectOutputStream(new FileOutputStream(USER))) {
            oot.writeObject(users);

            oot.flush();
        }
        catch (IOException e) {
            System.out.println(USER + ": IOException");
        }
    }

    private void saveCourses() {
        try (ObjectOutputStream oot = new ObjectOutputStream(new FileOutputStream(COURSE))) {
            oot.writeObject(courses);

            oot.flush();
        }
        catch (IOException e) {
            System.out.println(COURSE + ": IOException");
        }
    }

    private void saveNews() {
        try (ObjectOutputStream oot = new ObjectOutputStream(new FileOutputStream(NEWS))) {
            oot.writeObject(news);

            oot.flush();
        }
        catch (IOException e) {
            System.out.println(NEWS + ": IOException");
        }
    }
}
