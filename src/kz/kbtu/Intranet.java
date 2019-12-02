package kz.kbtu;

import com.sun.org.apache.xpath.internal.operations.Or;
import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.*;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
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
            adminSession((Admin) user);
        }
        else if (user instanceof ORManager) {
            orManagerSession((ORManager) user);
        }
        else if (user instanceof Student) {
            studentSession((Student) user);
        }
        else if (user instanceof Teacher) {
            teacherSession((Teacher) user);
        }
        else if (user instanceof Manager) {
            managerSession((Manager) user);
        }
        else if (user instanceof Executor) {
            executorSession((Executor) user);
        }
    }

    /* ---------------------------------------------------- Admin --------------------------------------------------- */
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
                    users();
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
    private void users() {
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

    /* -------------------------------------------------- ORManager ------------------------------------------------- */
    private void orManagerSession(ORManager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Add course");
            System.out.println("2. Remove course");
            System.out.println("3. Show course");
            System.out.println("4. Offer course");

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
                case "4":
                    orManagerOffer(manager);
                    break;
            }
        }
    }

    /* ORManager - add */
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

            database.save();
        }
        else {
            System.err.println("Cannot create Course!");
        }
    }

    /* ORManager - remove */
    private void orManagerRemove(ORManager manager) {
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

    /* ORManager - show */
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

    /* ORManager - offer */
    private void orManagerOffer(ORManager manager) {
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
    private void studentSession(Student student) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show courses");
            System.out.println("2. Registration");
            System.out.println("3. News");
            System.out.println("4. Transcript");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    studentCourses(student);
                    break;
                case "2":
                    studentRegister(student);
                    break;
                case "3":
                    newses();
                    break;
                case "4":
                    studentTranscript(student);
                    break;
            }
        }
    }

    /* Student - courses */
    private void studentCourses(Student student) {
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

            studentCourse(student, courses.get(index-1));
        }
    }

    private void studentCourse(Student student, Course course) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show course info");
            System.out.println("2. Show course files");
            System.out.println("3. Show teacher info");
            System.out.println("4. Show marks");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    courseInfo(course);
                    break;
                case "2":
                    courseFiles(course);
                    break;
                case "3":
                    courseTeacher(course);
                    break;
                case "4":
                    studentCourseMarks(student, course);
                    break;
            }
        }
    }

    private void studentCourseMarks(Student student, Course course) {
        Marks marks = course.getMarks().get(student.getLogin());

        System.out.println("Attestation1 : " + marks.getAttestation1().getScore());
        System.out.println("Attestation2 : " + marks.getAttestation2().getScore());
        System.out.println("Final : " + marks.getFinale().getScore());
    }

    /* Student - register */
    private void studentRegister(Student student) {
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

            studentRegisterCourse(student, courses.get(index-1));
        }
    }

    private void studentRegisterCourse(Student student, Course course) {
        course.updateStatus(student.getLogin(), CourseStatus.CURRENT);
        course.openMarks(student.getLogin());

        database.save();
        System.out.println("Course registered!");
    }

    /* Student - transcript */
    private void studentTranscript(Student student) {
        List<Course> courses = student.getCourses();

        for (Course course: courses) {
            if (course.getStatus(student.getLogin()) != CourseStatus.FUTURE) {
                Marks marks = course.getMarks(student.getLogin());

                System.out.println(course.getName() + ": " + marks);
            }
        }
    }

    /* --------------------------------------------------- Teacher -------------------------------------------------- */
    private void teacherSession(Teacher teacher) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show courses");
            System.out.println("2. News");
            System.out.println("3. Write order");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    teacherCourses(teacher);
                    break;
                case "2":
                    newses();
                    break;
                case "3":
                    teacherOrder(teacher);
            }
        }
    }

    /* Teacher - courses */
    private void teacherCourses(Teacher teacher) {
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

            teacherCourse(teacher, courses.get(index-1));
        }
    }

    private void teacherCourse(Teacher teacher, Course course) {
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
                    courseInfo(course);
                    break;
                case "2":
                    courseFiles(course);
                    break;
                case "3":
                    courseTeacher(course);
                    break;
                case "4":
                    courseStudents(course);
                    break;
                case "5":
                    teacherCourseFile(teacher, course);
                    break;
                case "6":
                    teacherPutMark(teacher, course);
                    break;
            }
        }
    }

    /* Teacher - create file */
    private void teacherCourseFile(Teacher teacher, Course course) {
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

    /* Teacher - put mark */
    private void teacherPutMark(Teacher teacher, Course course) {
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

    /* Teacher - order */
    private void teacherOrder(Teacher teacher) {
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

    /* --------------------------------------------------- Manager -------------------------------------------------- */
    private void managerSession(Manager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Add news");
            System.out.println("2. Show news");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    managerWrite(manager);
                    break;
                case "2":
                    newses();
                    break;
            }
        }
    }

    private void managerWrite(Manager manager) {
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
    private void executorSession(Executor executor) {
        String answer = "";

        while (!answer.equals(BACK)) {
            System.out.println("1. Show new orders");
            System.out.println("2. Show accepted orders");
            System.out.println("3. Show rejected orders");
            System.out.println("4. Show finished orders");
            System.out.println("5. Show news");

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    executorOrders(executor, OrderStatus.NEW);
                    break;
                case "2":
                    executorOrders(executor, OrderStatus.PENDING);
                    break;
                case "3":
                    executorOrders(executor, OrderStatus.REJECTED);
                    break;
                case "4":
                    executorOrders(executor, OrderStatus.FINISHED);
                    break;
                case "5":
                    newses();
                    break;
            }
        }
    }

    private void executorOrders(Executor executor, OrderStatus status) {
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

            executorOrder(executor, orders.get(index-1));
        }
    }

    private void executorOrder(Executor executor, Order order) {
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

    private void executorNew(Executor executor) {
        List<Order> orders = executor.getOrders();

        for (Order order: orders) {
            System.out.println(order);
        }
    }

    /* --------------------------------------------------- Course --------------------------------------------------- */
    private void courseInfo(Course course) {
        System.out.println(course);
    }

    private void courseFiles(Course course) {
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

            courseFile(files.get(index-1));
        }
    }

    private void courseFile(File file) {
        System.out.println(file);
    }

    private void courseTeacher(Course course) {
        System.out.println(course.getTeacher());
    }

    private void courseStudents(Course course) {
        for (Student student: course.getStudents()) {
            if (course.getStatus(student.getLogin()) == CourseStatus.CURRENT) {
                Marks marks = course.getMarks(student.getLogin());

                System.out.println(String.format("%s [%s] - %.2f : %.2f : %.2f",
                        student.getFullName(), student.getLogin(), marks.getAttestation1().getScore(),
                        marks.getAttestation2().getScore(), marks.getFinale().getScore()));
            }
        }
    }

    /* ---------------------------------------------------- News ---------------------------------------------------- */
    private void newses() {
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

            news(newses.get(index-1));
        }
    }

    private void news(News news) {
        System.out.println(news);

        String answer = "";

        while (!answer.equals(BACK)) {
            answer = SCANNER.nextLine();
        }
    }
}
