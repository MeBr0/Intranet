package kz.kbtu;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.*;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
import kz.kbtu.communication.message.Message;
import kz.kbtu.communication.message.Messaging;
import kz.kbtu.communication.news.News;
import kz.kbtu.communication.order.Order;
import kz.kbtu.communication.order.OrderStatus;
import kz.kbtu.study.File;
import kz.kbtu.study.Marks;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.CourseStatus;
import kz.kbtu.study.course.MarkMode;
import kz.kbtu.study.throwable.NotCurrentCourse;
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
        LOGGER = Logger.getInstance();
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
            innerSession((Admin) user);
        }
        else if (user instanceof ORManager) {
            innerSession((ORManager) user);
        }
        else if (user instanceof Student) {
            innerSession((Student) user);
        }
        else if (user instanceof Teacher) {
            innerSession((Teacher) user);
        }
        else if (user instanceof Manager) {
            innerSession((Manager) user);
        }
        else if (user instanceof Executor) {
            innerSession((Executor) user);
        }
    }

    /* ---------------------------------------------------- Admin --------------------------------------------------- */
    private void innerSession(Admin admin) {
        LOGGER.enterAdmin(admin);

        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Add user");
            System.out.println("2. Remove user");
            System.out.println("3. Show users");
            System.out.println("4. Change password");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    addUser(admin);
                    break;
                case "2":
                    removeUser(admin);
                    break;
                case "3":
                    showUsers();
                    break;
                case "4":
                    changePassword(admin);
                    break;
            }
        }
    }

    /* Add user */
    private void addUser(Admin admin) {
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
                    addStudent(admin);
                    break;
                case "2":
                    addManager(admin);
                    break;
                case "3":
                    addORManager(admin);
                    break;
                case "4":
                    addTeacher(admin);
                    break;
                case "5":
                    addExecutor(admin);
                    break;
                case "6":
                    addAdmin(admin);
                    break;
            }
        }

        database.save();
    }

    private void addStudent(Admin admin) {
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

    private void addManager(Admin admin) {
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

    private void addORManager(Admin admin) {
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

    private void addTeacher(Admin admin) {
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

    private void addExecutor(Admin admin) {
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

    private void addAdmin(Admin admin) {
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

    /* Remove user */
    private void removeUser(Admin admin) {
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

    /* Show users */
    private void showUsers() {
        String answer = "";

        while (true) {
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

            for (int i = 0; i < users.size(); ++i) {
                System.out.println(i+1 + ": " + users.get(i));
            }

            System.out.println("Choose user!");

            answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showUser(users.get(index-1));
        }
    }

    private void showUser(User user) {
        System.out.println(user);

        String answer = "";

        while (!answer.equals(BACK)) {
            answer = SCANNER.nextLine();
        }
    }

    /* -------------------------------------------------- ORManager ------------------------------------------------- */
    private void innerSession(ORManager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Add course");
            System.out.println("2. Remove course");
            System.out.println("3. Show course");
            System.out.println("4. Offer course");
            System.out.println("5. Show messages");
            System.out.println("6. Write message");
            System.out.println("7. News");
            System.out.println("8. Change password");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    addCourse(manager);
                    break;
                case "2":
                    removeCourse(manager);
                    break;
                case "3":
                    showCourses();
                    break;
                case "4":
                    offerCourse(manager);
                    break;
                case "5":
                    showMessages(manager);
                    break;
                case "6":
                    writeMessage(manager);
                    break;
                case "7":
                    showNewses();
                    break;
                case "8":
                    changePassword(manager);
                    break;
            }
        }
    }

    /* Add course */
    private void addCourse(ORManager manager) {
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

            database.save();
        }
        else {
            System.err.println("Cannot create Course!");
        }
    }

    /* Remove course */
    private void removeCourse(ORManager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("Write name of course to remove!");

            answer = SCANNER.nextLine();

            Course result = database.removeCourse(answer);

            if (result != null) {
                System.out.println("Course removed!");
                LOGGER.removeCourse(manager, result);

                database.save();
            }
            else {
                System.out.println("Cannot find such course!");
            }
        }

        database.save();
    }

    /* Show courses */
    private void showCourses() {
        List<Course> courses = database.getCourses();

        String answer = "";

        while (!answer.equals(BACK)) {
            for (int i = 0; i < courses.size(); ++i) {
                System.out.println(i+1 + ". " + courses.get(i));
            }

            answer = SCANNER.nextLine();
        }
    }

    /* Offer course */
    private void offerCourse(ORManager manager) {
        System.out.println("Type name of course!");
        String name = SCANNER.nextLine();

        System.out.println("Choose faculty!");
        Faculty[] faculties = database.getFaculties();
        for (int i = 0; i < faculties.length; ++i) {
            System.out.println(i+1 + ". " + faculties[i]);
        }
        int index = SCANNER.nextInt();
        Faculty faculty = faculties[index-1];

        System.out.println("Choose degree!");
        Degree[] degrees = database.getDegrees();
        for (int i = 0; i < degrees.length; ++i) {
            System.out.println(i+1 + ". " + degrees[i]);
        }
        index = SCANNER.nextInt();
        Degree degree = degrees[index-1];

        System.out.println("Type year of study!");
        int yearOfStudy = SCANNER.nextInt();

        Course course = database.getCourse(name);
        List<Student> students = database.getStudents(yearOfStudy, faculty, degree);

        if (course != null) {
            manager.offerCourse(course, students);

            LOGGER.offerCourse(manager, course, yearOfStudy, faculty, degree);
            System.out.println("Course offered!");

            database.save();
        }
    }

    /* --------------------------------------------------- Student -------------------------------------------------- */
    private void innerSession(Student student) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show courses");
            System.out.println("2. Registration");
            System.out.println("3. Transcript");
            System.out.println("4. News");
            System.out.println("5. Change password");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    showCourses(student);
                    break;
                case "2":
                    registerCourses(student);
                    break;
                case "3":
                    showTranscript(student);
                    break;
                case "4":
                    showNewses();
                    break;
                case "5":
                    changePassword(student);
                    break;
            }
        }
    }

    /* Show courses */
    private void showCourses(Student student) {
        List<Course> courses = new ArrayList<>();

        for (Course course: student.getCourses()) {
            if (course.getStatuses().get(student.getLogin()) == CourseStatus.CURRENT) {
                courses.add(course);
            }
        }

        while (true) {
            for (int i = 0; i < courses.size(); ++i) {
                System.out.println(i+1 + ". " + courses.get(i));
            }

            System.out.println("Choose course!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showCourse(student, courses.get(index-1));
        }
    }

    private void showCourse(Student student, Course course) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show course info");
            System.out.println("2. Show course files");
            System.out.println("3. Show teacher info");
            System.out.println("4. Show marks");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    showCourseInfo(course);
                    break;
                case "2":
                    showFiles(course);
                    break;
                case "3":
                    showTeacherInfo(course);
                    break;
                case "4":
                    showMarks(student, course);
                    break;
            }
        }
    }

    private void showMarks(Student student, Course course) {
        Marks marks = course.getMarks().get(student.getLogin());

        System.out.println("Attestation1 : " + marks.getAttestation1().getScore());
        System.out.println("Attestation2 : " + marks.getAttestation2().getScore());
        System.out.println("Final : " + marks.getFinale().getScore());
    }

    /* Register course */
    private void registerCourses(Student student) {
        while (true) {
            List<Course> courses = new ArrayList<>();

            for (Course course: student.getCourses()) {
                if (course.getStatus(student.getLogin()) == CourseStatus.FUTURE) {
                    courses.add(course);
                }
            }

            for (int i = 0; i < courses.size(); ++i) {
                System.out.println(i+1 + ". " + courses.get(i));
            }

            System.out.println("Choose course to register!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            registerCourse(student, courses.get(index-1));
        }
    }

    private void registerCourse(Student student, Course course) {
        course.updateStatus(student.getLogin(), CourseStatus.CURRENT);
        course.openMarks(student.getLogin());

        database.save();
        System.out.println("Course registered!");
    }

    /* Show transcript */
    private void showTranscript(Student student) {
        List<Course> courses = student.getCourses();

        for (Course course: courses) {
            if (course.getStatus(student.getLogin()) != CourseStatus.FUTURE) {
                Marks marks = course.getMarks(student.getLogin());

                System.out.println(course.getName() + ": " + marks);
            }
        }
    }

    /* --------------------------------------------------- Teacher -------------------------------------------------- */
    private void innerSession(Teacher teacher) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show courses");
            System.out.println("2. Add news");
            System.out.println("3. Write order");
            System.out.println("4. Show messages");
            System.out.println("5. Write message");
            System.out.println("6. News");
            System.out.println("7. Change password");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    showCourses(teacher);
                    break;
                case "2":
                    writeNews(teacher);
                    break;
                case "3":
                    sendOrder(teacher);
                    break;
                case "4":
                    showMessages(teacher);
                    break;
                case "5":
                    writeMessage(teacher);
                    break;
                case "6":
                    showNewses();
                    break;
                case "7":
                    changePassword(teacher);
                    break;
            }
        }
    }

    /* Show courses */
    private void showCourses(Teacher teacher) {
        List<Course> courses = teacher.getCourses();

        while (true) {
            for (int i = 0; i < courses.size(); ++i) {
                System.out.println(i+1 + ". " + courses.get(i));
            }

            System.out.println("Choose course!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showCourse(teacher, courses.get(index-1));
        }
    }

    private void showCourse(Teacher teacher, Course course) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show course info");
            System.out.println("2. Show course files");
            System.out.println("3. Show teacher info");
            System.out.println("4. Show students");
            System.out.println("5. Upload file");
            System.out.println("6. Put marks");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    showCourseInfo(course);
                    break;
                case "2":
                    showFiles(course);
                    break;
                case "3":
                    showTeacherInfo(course);
                    break;
                case "4":
                    showStudents(course);
                    break;
                case "5":
                    uploadFile(teacher, course);
                    break;
                case "6":
                    putMarks(teacher, course);
                    break;
            }
        }
    }

    /* Upload file */
    private void uploadFile(Teacher teacher, Course course) {
        System.out.println("Type title of file!");
        String title = SCANNER.nextLine();

        System.out.println("Type text of file!");
        String text = SCANNER.nextLine();

        File file = teacher.createFile(title, text);

        course.uploadFile(file);
        System.out.println("File uploaded!");
        LOGGER.uploadFile(teacher, course, file);

        database.save();
    }

    /* Put marks */
    private void putMarks(Teacher teacher, Course course) {
        System.out.println("Type login of student!");
        String login = SCANNER.nextLine();

        System.out.println("Choose mode of mark!");
        MarkMode[] modes = database.getMarkModes();
        for (int i = 0; i < modes.length; ++i) {
            System.out.println(i+1 + ". " + modes[i]);
        }
        int index = SCANNER.nextInt();
        MarkMode mode = modes[index-1];

        System.out.println("Type score to add for student!");
        double delta = SCANNER.nextDouble();

        try {
            teacher.putMark(login, course, mode, delta);
            System.out.println("Mark put!");

            database.save();
        }
        catch (NotCurrentCourse notCurrentCourse) {
            System.err.println(notCurrentCourse.getMessage());
        }

    }

    /* Send order */
    private void sendOrder(Teacher teacher) {
        System.out.println("Type title of order!");
        String title = SCANNER.nextLine();

        System.out.println("Type text of order!");
        String text = SCANNER.nextLine();

        System.out.println("Type login of executor!");
        String login = SCANNER.nextLine();

        User user = database.getUser(login);

        if (user instanceof Executor) {
            Executor executor = (Executor) user;
            Order order = teacher.sendOrder(title, text, executor);
            System.out.println("Order sent!");
            LOGGER.sendOrder(teacher, order, executor);
            database.save();
        }
        else {
            System.err.println("Cannot create Order!");
        }
    }

    /* Write news */
    private void writeNews(Teacher teacher) {
        System.out.println("Type title of news!");
        String title = SCANNER.nextLine();

        System.out.println("Type text of news!");
        String text = SCANNER.nextLine();

        News news = teacher.createNews(title, text);
        database.addNews(news);
        System.out.println("News created!");
        LOGGER.writeNews(teacher, news);
        database.save();
    }

    /* --------------------------------------------------- Manager -------------------------------------------------- */
    private void innerSession(Manager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Add news");
            System.out.println("2. Show messages");
            System.out.println("3. Write message");
            System.out.println("4. News");
            System.out.println("5. Change password");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    writeNews(manager);
                    break;
                case "2":
                    showMessages(manager);
                    break;
                case "3":
                    writeMessage(manager);
                    break;
                case "4":
                    showNewses();
                    break;
                case "5":
                    changePassword(manager);
                    break;
            }
        }
    }

    /* Write news */
    private void writeNews(Manager manager) {
        System.out.println("Type title of news!");
        String title = SCANNER.nextLine();

        System.out.println("Type text of news!");
        String text = SCANNER.nextLine();

        News news = manager.createNews(title, text);
        database.addNews(news);
        System.out.println("News created!");
        LOGGER.writeNews(manager, news);
        database.save();
    }

    /* -------------------------------------------------- Executor -------------------------------------------------- */
    private void innerSession(Executor executor) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show new orders");
            System.out.println("2. Show accepted orders");
            System.out.println("3. Show rejected orders");
            System.out.println("4. Show finished orders");
            System.out.println("5. Show messages");
            System.out.println("6. Write message");
            System.out.println("7. News");
            System.out.println("8. Change password");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    showOrders(executor, OrderStatus.NEW);
                    break;
                case "2":
                    showOrders(executor, OrderStatus.PENDING);
                    break;
                case "3":
                    showOrders(executor, OrderStatus.REJECTED);
                    break;
                case "4":
                    showOrders(executor, OrderStatus.FINISHED);
                    break;
                case "5":
                    showMessages(executor);
                    break;
                case "6":
                    writeMessage(executor);
                    break;
                case "7":
                    showNewses();
                    break;
                case "8":
                    changePassword(executor);
                    break;
            }
        }
    }

    /* Show orders */
    private void showOrders(Executor executor, OrderStatus status) {
        while (true) {
            List<Order> orders = executor.getOrders(status);

            if (orders.size() == 0) {
                System.out.println("Empty!");
                return;
            }

            for (int i = 0; i < orders.size(); ++i) {
                System.out.println(i+1 + ". " + orders.get(i));
            }

            System.out.println("Choose order!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showOrder(executor, orders.get(index-1));
        }
    }

    private void showOrder(Executor executor, Order order) {
        String answer;

        System.out.println(order);

        if (order.getStatus() == OrderStatus.NEW) {
            System.out.println("This is new order! Do you accept or reject?");
            System.out.println("1. Accept order");
            System.out.println("2. Reject order");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    executor.changeOrderStatus(order, OrderStatus.PENDING);
                    System.out.println("Order accepted! (now pending)");
                    break;
                case "2":
                    executor.changeOrderStatus(order, OrderStatus.REJECTED);
                    System.out.println("Order rejected!");
                    break;
            }
        }
        else if (order.getStatus() == OrderStatus.PENDING) {
            System.out.println("This is pending order! Do you finished it?");
            System.out.println("1. Finish order");
            System.out.println("2. Reject order");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    executor.changeOrderStatus(order, OrderStatus.FINISHED);
                    System.out.println("Order finished!");
                    break;
                case "2":
                    executor.changeOrderStatus(order, OrderStatus.REJECTED);
                    System.out.println("Order rejected!");
                    break;
            }
        }
        else if (order.getStatus() == OrderStatus.REJECTED) {
            System.out.println("This is rejected order! Do you accept it?");
            System.out.println("1. Accept order");

            answer = SCANNER.nextLine();

            if ("1".equals(answer)) {
                executor.changeOrderStatus(order, OrderStatus.PENDING);
                System.out.println("Order accepted! (now pending)");
            }
        }
        else {
            System.out.println("This is finished order! Do you undone it?");
            System.out.println("1. Undone order");

            answer = SCANNER.nextLine();

            if ("1".equals(answer)) {
                executor.changeOrderStatus(order, OrderStatus.PENDING);
                System.out.println("Order accepted! (now pending)");
            }
        }

        database.save();
    }

    /* --------------------------------------------------- Other ---------------------------------------------------- */

    /* Show course info */
    private void showCourseInfo(Course course) {
        System.out.println(course);
    }

    /* Show files */
    private void showFiles(Course course) {
        List<File> files = course.getFiles();

        while (true) {
            for (int i = 0; i < files.size(); ++i) {
                System.out.println(i+1 + ". " + files.get(i).getTitle());
            }

            System.out.println("Choose file!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showFile(files.get(index-1));
        }
    }

    private void showFile(File file) {
        System.out.println(file);
    }

    /* Show teacher info */
    private void showTeacherInfo(Course course) {
        System.out.println(course.getTeacher());
    }

    /* Show students */
    private void showStudents(Course course) {
        for (Student student: course.getStudents()) {
            if (course.getStatus(student.getLogin()) == CourseStatus.CURRENT) {
                Marks marks = course.getMarks(student.getLogin());

                System.out.println(String.format("%s [%s] - %.2f : %.2f : %.2f",
                        student.getFullName(), student.getLogin(), marks.getAttestation1().getScore(),
                        marks.getAttestation2().getScore(), marks.getFinale().getScore()));
            }
        }
    }

    /* Show messages */
    private void showMessages(Messaging messaging) {
        while (true) {
            List<Message> messages = messaging.getMessages();

            if (messages.size() == 0) {
                System.out.println("Empty!");
                return;
            }

            for (int i = 0; i < messages.size(); ++i) {
                System.out.println(i+1 + ". " + messages.get(i));
            }

            System.out.println("Choose message!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showMessage(messages.get(index-1));
        }
    }

    private void showMessage(Message message) {
        System.out.println(message);

        String answer = "";

        while (!answer.equals(BACK)) {
            answer = SCANNER.nextLine();
        }
    }

    /* Write message */
    private void writeMessage(Messaging messaging) {
        System.out.println("Type title of message!");
        String title = SCANNER.nextLine();

        System.out.println("Type text of message!");
        String text = SCANNER.nextLine();

        System.out.println("Type login of target!");
        String login = SCANNER.nextLine();

        User user = database.getUser(login);

        if (user instanceof Messaging) {
            Messaging target = (Messaging) user;
            messaging.sendMessage(title, text, target);
            System.out.println("Message sent!");
            database.save();
        }
        else {
            System.err.println("Cannot send Message!");
        }
    }

    /* Show newses */
    private void showNewses() {
        List<News> newses = database.getNews();

        while (true) {
            for (int i = 0; i < newses.size(); ++i) {
                System.out.println(i+1 + ": " + newses.get(i));
            }

            System.out.println("Choose news!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showNews(newses.get(index-1));
        }
    }

    private void showNews(News news) {
        System.out.println(news);

        String answer = "";

        while (!answer.equals(BACK)) {
            answer = SCANNER.nextLine();
        }
    }

    /* Change password */
    private void changePassword(User user) {
        System.out.println("Type current password!");
        String currentPassword = SCANNER.nextLine();

        System.out.println("Type new password!");
        String newPassword = SCANNER.nextLine();

        System.out.println("Type new password again!");
        String newPassword2 = SCANNER.nextLine();

        if (user.checkCredentials(user.getLogin(), currentPassword) && newPassword.equals(newPassword2)) {
            user.setPassword(newPassword);

            System.out.println("Password changed!");
        }
        else {
            System.err.println("Cannot change password!");
        }
    }
}
