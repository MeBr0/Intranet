package kz.kbtu;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.*;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
import kz.kbtu.study.course.Course;
import kz.kbtu.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Intranet {

    private Database database;
    private final Scanner SCANNER;
    private final Logger LOGGER;

    private final String BACK = "q";

    {
        database = new Database();

        SCANNER = new Scanner(System.in);
        LOGGER = new Logger();
    }

    public void begin() {
        database.save();
        System.out.println(database.getUsers());
        System.out.println("Welcome to Intranet system!");

        int i = 0;

        while (i < 3) {
            System.out.println("Enter login please!");
            String login = SCANNER.nextLine();

            if (login.equals(BACK))
                break;

            System.out.println("Enter password please!");
            String password = SCANNER.nextLine();

            i++;

            User user = database.getUser(login, password);

            if (user != null) {
                session(user);
                break;
            }
            else {
                System.out.println("Invalid credentials! Try again!");
            }
        }

        database.save();
    }

    private void session(User user) {
        System.out.println("You began session as " + user.getClass().getSimpleName());

        if (user instanceof Admin) {
            adminSession((Admin) user);
        }
        else if (user instanceof ORManager) {
            orManagerSession((ORManager) user);
        }
    }

    /* Admin */
    private void adminSession(Admin admin) {
        LOGGER.enterAdmin(admin);

        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Add user");
            System.out.println("2. Remove user");
            System.out.println("3. Show users");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    adminAdd(admin);
                    break;
                case "2":
                    adminRemove(admin);
                    break;
                case "3":
                    adminShow();
                    break;
            }
        }
    }

    /* Admin - add */
    private void adminAdd(Admin admin) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Students");
            System.out.println("2. Manager");
            System.out.println("3. ORManagers");
            System.out.println("4. Teachers");
            System.out.println("5. Executors");
            System.out.println("6. Admins");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    adminAddStudent(admin);
                    break;
                case "2":
                    adminAddManager(admin);
                    break;
                case "3":
                    adminAddOrManager(admin);
                    break;
                case "4":
                    adminAddTeacher(admin);
                    break;
                case "5":
                    adminAddExecutor(admin);
                    break;
                case "6":
                    adminAddAdmin(admin);
                    break;
            }
        }

        database.save();
    }

    private void adminAddStudent(Admin admin) {
        System.out.println("Type login of user!");
        String login = SCANNER.nextLine();

        System.out.println("Type first name of user!");
        String firstName = SCANNER.nextLine();

        System.out.println("Type last name of user!");
        String lastName = SCANNER.nextLine();

        System.out.println("Choose faculty of student!");
        Faculty[] faculties = database.getFaculties();
        for (int i = 0; i < faculties.length; ++i) {
            System.out.println(i+1 + ". " + faculties[i]);
        }
        int index = SCANNER.nextInt();
        Faculty faculty = faculties[index-1];

        System.out.println("Choose degree of student!");
        Degree[] degrees = database.getDegrees();
        for (int i = 0; i < degrees.length; ++i) {
            System.out.println(i+1 + ". " + degrees[i]);
        }
        index = SCANNER.nextInt();
        Degree degree = degrees[index-1];

        Student student = admin.createStudent(faculty, degree, login, firstName, lastName);
        database.addUser(student);

        LOGGER.createUser(admin, student);
        System.out.println("Student created!");
    }

    private void adminAddManager(Admin admin) {
        System.out.println("Type login of user!");
        String login = SCANNER.nextLine();

        System.out.println("Type first name of user!");
        String firstName = SCANNER.nextLine();

        System.out.println("Type last name of user!");
        String lastName = SCANNER.nextLine();

        System.out.println("Choose faculty of manager!");
        Faculty[] faculties = database.getFaculties();
        for (int i = 0; i < faculties.length; ++i) {
            System.out.println(i+1 + ". " + faculties[i]);
        }
        int index = SCANNER.nextInt();
        Faculty faculty = faculties[index-1];

        System.out.println("Type salary of employee!");
        int salary = SCANNER.nextInt();

        Manager manager = admin.createManager(faculty, salary, login, firstName, lastName);
        database.addUser(manager);

        LOGGER.createUser(admin, manager);
        System.out.println("Manager created!");
    }

    private void adminAddOrManager(Admin admin) {
        System.out.println("Type login of user!");
        String login = SCANNER.nextLine();

        System.out.println("Type first name of user!");
        String firstName = SCANNER.nextLine();

        System.out.println("Type last name of user!");
        String lastName = SCANNER.nextLine();

        System.out.println("Type salary of employee!");
        int salary = SCANNER.nextInt();

        ORManager manager = admin.createOrManager(salary, login, firstName, lastName);
        database.addUser(manager);

        LOGGER.createUser(admin, manager);
        System.out.println("ORManager created!");
    }

    private void adminAddTeacher(Admin admin) {
        System.out.println("Type login of user!");
        String login = SCANNER.nextLine();

        System.out.println("Type first name of user!");
        String firstName = SCANNER.nextLine();

        System.out.println("Type last name of user!");
        String lastName = SCANNER.nextLine();

        System.out.println("Choose faculty of teacher!");
        Faculty[] faculties = database.getFaculties();
        for (int i = 0; i < faculties.length; ++i) {
            System.out.println(i+1 + ". " + faculties[i]);
        }
        int index = SCANNER.nextInt();
        Faculty faculty = faculties[index-1];

        System.out.println("Choose faculty of teacher!");
        TeacherPosition[] positions = database.getPositions();
        for (int i = 0; i < positions.length; ++i) {
            System.out.println(i+1 + ". " + positions[i]);
        }
        index = SCANNER.nextInt();
        TeacherPosition position = positions[index-1];

        System.out.println("Type salary of employee!");
        int salary = SCANNER.nextInt();

        Teacher teacher = admin.createTeacher(faculty, position, salary, login, firstName, lastName);
        database.addUser(teacher);

        LOGGER.createUser(admin, teacher);
        System.out.println("Teacher created!");
    }

    private void adminAddExecutor(Admin admin) {
        System.out.println("Type login of user!");
        String login = SCANNER.nextLine();

        System.out.println("Type first name of user!");
        String firstName = SCANNER.nextLine();

        System.out.println("Type last name of user!");
        String lastName = SCANNER.nextLine();

        System.out.println("Type salary of employee!");
        int salary = SCANNER.nextInt();

        Executor executor = admin.createExecutor(salary, login, firstName, lastName);
        database.addUser(executor);

        LOGGER.createUser(admin, executor);
        System.out.println("Executor created!");
    }

    private void adminAddAdmin(Admin admin) {
        System.out.println("Type login of user!");
        String login = SCANNER.nextLine();

        System.out.println("Type first name of user!");
        String firstName = SCANNER.nextLine();

        System.out.println("Type last name of user!");
        String lastName = SCANNER.nextLine();

        Admin newAdmin = Admin.createAdmin(login, firstName, lastName);
        database.addUser(newAdmin);

        LOGGER.createUser(admin, newAdmin);
        System.out.println("Admin created!");
    }

    /* Admin - remove */
    private void adminRemove(Admin admin) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("Write login of user to remove!");

            answer = SCANNER.nextLine();

            User result = database.removeUser(answer);

            if (result != null) {
                System.out.println("User removed!");
                LOGGER.removeUser(admin, result);
            }
            else {
                System.out.println("Cannot find such user!");
            }
        }

        database.save();
    }

    /* Admin - show */
    private void adminShow() {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("Choose users!");
            System.out.println("1. Students");
            System.out.println("2. Manager");
            System.out.println("3. ORManagers");
            System.out.println("4. Teachers");
            System.out.println("5. Executors");
            System.out.println("6. Admins");

            answer = SCANNER.nextLine();

            List<User> users = new ArrayList<>();

            switch (answer) {
                case "1":
                    users = database.getUsers(Student.class);
                    break;
                case "2":
                    users = database.getUsers(Manager.class);
                    break;
                case "3":
                    users = database.getUsers(ORManager.class);
                    break;
                case "4":
                    users = database.getUsers(Teacher.class);
                    break;
                case "5":
                    users = database.getUsers(Executor.class);
                    break;
                case "6":
                    users = database.getUsers(Admin.class);
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }

            for (User user: users) {
                System.out.println(user);
            }
        }
    }

    /* ORManager */
    private void orManagerSession(ORManager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Add course");
            System.out.println("2. Remove course");
            System.out.println("3. Show course");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    orManagerAdd(manager);
                    break;
                case "2":
                    orManagerRemove(manager);
                    break;
                case "3":
                    orManagerShow();
                    break;
            }
        }
    }

    private void orManagerAdd(ORManager manager) {
        System.out.println("Type name of course!");
        String name = SCANNER.nextLine();

        System.out.println("Type login of teacher of course!");
        String login = SCANNER.nextLine();

        System.out.println("Type credit number of course!");
        int creditNumber = SCANNER.nextInt();

        User user = database.getUser(login);

        if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;

            Course course = manager.createCourse(name, creditNumber, teacher);
            database.addCourse(course);

            LOGGER.createCourse(manager, course);
            System.out.println("Course created!");
        }
        else {
            System.err.println("Cannot create Course!");
        }
    }

    private void orManagerRemove(ORManager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("Write name of course to remove!");

            answer = SCANNER.nextLine();

            Course result = database.removeCourse(answer);

            if (result != null) {
                System.out.println("Course removed!");
                LOGGER.removeCourse(manager, result);
            }
            else {
                System.out.println("Cannot find such course!");
            }
        }

        database.save();
    }

    private void orManagerShow() {
        List<Course> courses = database.getCourses();

        String answer = "";

        while (!answer.equals(BACK)) {
            for (int i = 0; i < courses.size(); ++i) {
                System.out.println(i+1 + ". " + courses.get(i));
            }

            answer = SCANNER.nextLine();
        }
    }
}
