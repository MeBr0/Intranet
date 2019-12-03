package kz.kbtu;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.*;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
import kz.kbtu.communication.message.Message;
import kz.kbtu.communication.message.Messaging;
import kz.kbtu.communication.news.ManagingNews;
import kz.kbtu.communication.news.News;
import kz.kbtu.communication.order.Order;
import kz.kbtu.communication.order.OrderStatus;
import kz.kbtu.communication.order.SendingOrders;
import kz.kbtu.study.File;
import kz.kbtu.study.Marks;
import kz.kbtu.study.course.Course;
import kz.kbtu.study.course.CourseStatus;
import kz.kbtu.study.course.MarkMode;
import kz.kbtu.study.throwable.NotCurrentCourse;
import kz.kbtu.util.Logger;
import kz.kbtu.util.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class Intranet {

    private final Database DATABASE;
    private final Logger LOGGER;
    private final Printer PRINTER;
    private final Scanner SCANNER;

    private final String BACK = "q";

    {
        DATABASE = Database.getInstance();
        LOGGER = Logger.getInstance();
        PRINTER = Printer.getInstance();
        SCANNER = new Scanner(System.in);
    }

    public void begin() {
//        System.out.println(DATABASE.getUsers());
        printInfo("Welcome to Intranet system!");

        int i = 0;

        while (i < 3) {
            printInfo("Enter login please!");
            String login = SCANNER.nextLine();

            if (login.equals(BACK))
                break;

            printInfo("Enter password please!");
            String password = SCANNER.nextLine();

            i++;

            User user = DATABASE.getUser(login, password);

            if (user != null) {
                session(user);
                break;
            }
            else {
                printError("Invalid credentials! Try again!");
            }
        }

        DATABASE.save();
    }

    private void session(User user) {
        printResult(String.format("Hello %s [%s]", user.getFullName(), user.getLogin()));
        printResult("You entered as " + user.getClass().getSimpleName());

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
            String[] options = new String[] { "Add user", "Remove user", "Show users", "Change password" };
            printOptions(options);

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
        while (true) {
            String[] options = new String[] { "Students", "Managers", "ORManagers", "Teachers", "Executors", "Admins" };
            printOptions(options);

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                return;

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

            DATABASE.save();
        }
    }

    private void addStudent(Admin admin) {
        printInfo("Type login of student!");
        String login = SCANNER.nextLine();

        printInfo("Type first name of student!");
        String firstName = SCANNER.nextLine();

        printInfo("Type last name of student!");
        String lastName = SCANNER.nextLine();

        printInfo("Choose faculty of student!");
        Faculty[] faculties = DATABASE.getFaculties();;
        printFaculties(faculties);
        int index = SCANNER.nextInt();
        Faculty faculty = faculties[index-1];

        printInfo("Choose degree of student!");
        Degree[] degrees = DATABASE.getDegrees();
        printDegrees(degrees);
        index = SCANNER.nextInt();
        Degree degree = degrees[index-1];

        SCANNER.nextLine();

        if (DATABASE.isValidLogin(login)) {
            Student student = admin.createStudent(faculty, degree, login, firstName, lastName);
            DATABASE.addUser(student);

            LOGGER.createUser(admin, student);
            printResult("Student created!");
        }
        else {
            printError("Such login exists!");
        }
    }

    private void addManager(Admin admin) {
        printInfo("Type login of manager!");
        String login = SCANNER.nextLine();

        printInfo("Type first name of manager!");
        String firstName = SCANNER.nextLine();

        printInfo("Type last name of manager!");
        String lastName = SCANNER.nextLine();

        printInfo("Choose faculty of manager!");
        Faculty[] faculties = DATABASE.getFaculties();
        printFaculties(faculties);
        int index = SCANNER.nextInt();
        Faculty faculty = faculties[index-1];

        printInfo("Type salary of manager!");
        int salary = SCANNER.nextInt();

        SCANNER.nextLine();

        if (DATABASE.isValidLogin(login)) {
            Manager manager = admin.createManager(faculty, salary, login, firstName, lastName);
            DATABASE.addUser(manager);

            LOGGER.createUser(admin, manager);
            printResult("Manager created!");
        }
        else {
            printError("Such login exists!");
        }
    }

    private void addORManager(Admin admin) {
        printInfo("Type login of OR manager!");
        String login = SCANNER.nextLine();

        printInfo("Type first name of OR manager!");
        String firstName = SCANNER.nextLine();

        printInfo("Type last name of OR manager!");
        String lastName = SCANNER.nextLine();

        printInfo("Type salary of OR manager!");
        int salary = SCANNER.nextInt();

        SCANNER.nextLine();

        if (DATABASE.isValidLogin(login)) {
            ORManager manager = admin.createOrManager(salary, login, firstName, lastName);
            DATABASE.addUser(manager);

            LOGGER.createUser(admin, manager);
            printResult("OR Manager created!");
        }
        else {
            printError("Such login exists!");
        }
    }

    private void addTeacher(Admin admin) {
        printInfo("Type login of teacher!");
        String login = SCANNER.nextLine();

        printInfo("Type first name of teacher!");
        String firstName = SCANNER.nextLine();

        printInfo("Type last name of teacher!");
        String lastName = SCANNER.nextLine();

        printInfo("Choose faculty of teacher!");
        Faculty[] faculties = DATABASE.getFaculties();
        printFaculties(faculties);
        int index = SCANNER.nextInt();
        Faculty faculty = faculties[index-1];

        printInfo("Choose position of teacher!");
        TeacherPosition[] positions = DATABASE.getPositions();
        printPositions(positions);
        index = SCANNER.nextInt();
        TeacherPosition position = positions[index-1];

        printInfo("Type salary of teacher!");
        int salary = SCANNER.nextInt();

        SCANNER.nextLine();

        if (DATABASE.isValidLogin(login)) {
            Teacher teacher = admin.createTeacher(faculty, position, salary, login, firstName, lastName);
            DATABASE.addUser(teacher);

            LOGGER.createUser(admin, teacher);
            printResult("Teacher created!");
        }
        else {
            printError("Such login exists!");
        }
    }

    private void addExecutor(Admin admin) {
        printInfo("Type login of executor!");
        String login = SCANNER.nextLine();

        printInfo("Type first name of executor!");
        String firstName = SCANNER.nextLine();

        printInfo("Type last name of executor!");
        String lastName = SCANNER.nextLine();

        printInfo("Type salary of executor!");
        int salary = SCANNER.nextInt();

        SCANNER.nextLine();

        if (DATABASE.isValidLogin(login)) {
            Executor executor = admin.createExecutor(salary, login, firstName, lastName);
            DATABASE.addUser(executor);

            LOGGER.createUser(admin, executor);
            printResult("Executor created!");
        }
        else {
            printError("Such login exists!");
        }
    }

    private void addAdmin(Admin admin) {
        printInfo("Type login of admin!");
        String login = SCANNER.nextLine();

        printInfo("Type first name of admin!");
        String firstName = SCANNER.nextLine();

        printInfo("Type last name of admin!");
        String lastName = SCANNER.nextLine();

        if (DATABASE.isValidLogin(login)) {
            Admin newAdmin = Admin.createAdmin(login, firstName, lastName);
            DATABASE.addUser(newAdmin);

            LOGGER.createUser(admin, newAdmin);
            printResult("Admin created!");
        }
        else {
            printError("Such login exists!");
        }
    }

    /* Remove user */
    private void removeUser(Admin admin) {
        String answer = "";

        while (!answer.equals(BACK)) {
            printInfo("Write login of user to remove!");

            answer = SCANNER.nextLine();

            User result = DATABASE.removeUser(answer);

            if (result != null) {
                LOGGER.removeUser(admin, result);
                printResult("User removed!");
            }
            else {
                printError("Login not found!");
            }
        }

        DATABASE.save();
    }

    /* Show users */
    private void showUsers() {
        while (true) {
            printInfo("Choose users!");
            String[] options = new String[] { "Students", "Managers", "ORManagers", "Teachers", "Executors", "Admins" };
            printOptions(options);

            String answer = SCANNER.nextLine();

            List<User> users = new ArrayList<>();

            switch (answer) {
                case "1":
                    users = DATABASE.getUsers(Student.class);
                    break;
                case "2":
                    users = DATABASE.getUsers(Manager.class);
                    break;
                case "3":
                    users = DATABASE.getUsers(ORManager.class);
                    break;
                case "4":
                    users = DATABASE.getUsers(Teacher.class);
                    break;
                case "5":
                    users = DATABASE.getUsers(Executor.class);
                    break;
                case "6":
                    users = DATABASE.getUsers(Admin.class);
                    break;
                default:
                    printError("Invalid option!");
                    break;
            }

            if (users.size() == 0) {
                printResult("Empty!");
                return;
            }

            for (int i = 0; i < users.size(); ++i) {
                System.out.println(i+1 + ": " + users.get(i).getFullName() + "[" + users.get(i).getLogin() + "]");
            }

            printInfo("Choose user!");

            answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showUser(users.get(index-1));
        }
    }

    private void showUser(User user) {
        System.out.println(String.format("Full name: %s [%s]", user.getFullName(), user.getLogin()));
        System.out.println(String.format("Birth date: %s", user.getBirthDate()));
        System.out.println(String.format("Gender: %s", user.getGender()));
        System.out.println(String.format("Phone number: %s", user.getPhoneNumber()));
        System.out.println(String.format("Email: %s", user.getEmail()));

        await();
    }

    /* -------------------------------------------------- ORManager ------------------------------------------------- */
    private void innerSession(ORManager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            String[] options = new String[] { "Add course", "Remove course", "Show course", "Offer course",
                    "Write order", "Show messages", "Write message", "News", "Change password" };
            printOptions(options);

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
                    sendOrder(manager);
                    break;
                case "6":
                    showMessages(manager);
                    break;
                case "7":
                    writeMessage(manager);
                    break;
                case "8":
                    showNewses();
                    break;
                case "9":
                    changePassword(manager);
                    break;
            }
        }
    }

    /* Add course */
    private void addCourse(ORManager manager) {
        printInfo("Type name of course!");
        String name = SCANNER.nextLine();

        printInfo("Type login of teacher of course!");
        String login = SCANNER.nextLine();

        printInfo("Type credit number of course!");
        int creditNumber = SCANNER.nextInt();

        SCANNER.nextLine();

        User user = DATABASE.getUser(login);

        if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;

            Course course = manager.createCourse(name, creditNumber, teacher);
            DATABASE.addCourse(course);

            LOGGER.createCourse(manager, course);
            printResult("Course created!");

            DATABASE.save();
        }
        else {
            printError("Login not found or not teacher's!");
        }
    }

    /* Remove course */
    private void removeCourse(ORManager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            printInfo("Write name of course to remove!");

            answer = SCANNER.nextLine();

            Course result = DATABASE.removeCourse(answer);

            if (result != null) {
                printResult("Course removed!");
                LOGGER.removeCourse(manager, result);

                DATABASE.save();
            }
            else {
                printError("Course not found!");
            }
        }

        DATABASE.save();
    }

    /* Show courses */
    private void showCourses() {
        while (true) {
            List<Course> courses = DATABASE.getCourses();

            if (courses.size() == 0) {
                printResult("Empty!");
                return;
            }

            for (int i = 0; i < courses.size(); ++i) {
                System.out.println(i+1 + ". " + courses.get(i).getName());
            }

            printInfo("Choose course!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showCourseInfo(courses.get(index-1));
        }
    }

    /* Offer course */
    private void offerCourse(ORManager manager) {
        printInfo("Type name of course!");
        String name = SCANNER.nextLine();

        printInfo("Choose faculty!");
        Faculty[] faculties = DATABASE.getFaculties();
        printFaculties(faculties);
        int index = SCANNER.nextInt();
        Faculty faculty = faculties[index-1];

        printInfo("Choose degree!");
        Degree[] degrees = DATABASE.getDegrees();
        printDegrees(degrees);
        index = SCANNER.nextInt();
        Degree degree = degrees[index-1];

        printInfo("Type year of study!");
        int yearOfStudy = SCANNER.nextInt();

        SCANNER.nextLine();

        Course course = DATABASE.getCourse(name);
        List<Student> students = DATABASE.getStudents(yearOfStudy, faculty, degree);

        if (course != null) {
            manager.offerCourse(course, students);

            LOGGER.offerCourse(manager, course, yearOfStudy, faculty, degree);
            printResult("Course offered!");
        }
        else {
            printError("Course not found!");
        }

        DATABASE.save();
    }

    /* --------------------------------------------------- Student -------------------------------------------------- */
    private void innerSession(Student student) {
        String answer = "";

        while (!answer.equals(BACK)) {
            String[] options = new String[] { "Show courses", "Registration", "Transcript", "News", "Change password" };
            printOptions(options);

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
        while (true) {
            List<Course> courses = new ArrayList<>();

            for (Course course: student.getCourses()) {
                if (course.getStatuses().get(student.getLogin()) == CourseStatus.CURRENT) {
                    courses.add(course);
                }
            }

            if (courses.size() == 0) {
                printResult("Empty!");
                return;
            }

            for (int i = 0; i < courses.size(); ++i) {
                System.out.println(i+1 + ". " + courses.get(i));
            }

            printInfo("Choose course!");

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
            String[] options = new String[] { "Show course info", "Show course files", "Show teacher info",
                    "Show marks" };
            printOptions(options);

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    showCourseInfo(course);
                    break;
                case "2":
                    showFiles(course);
                    break;
                case "3":
                    showTeacherInfo(course.getTeacher());
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

            if (courses.size() == 0) {
                printResult("Empty!");
                return;
            }

            for (int i = 0; i < courses.size(); ++i) {
                System.out.println(i+1 + ". " + courses.get(i));
            }

            printInfo("Choose course to register!");

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
        printResult("Course registered!");

        DATABASE.save();
    }

    /* Show transcript */
    private void showTranscript(Student student) {
        List<Course> courses = student.getCourses();

        if (courses.size() == 0) {
            printResult("Empty!");
            return;
        }

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
            String[] options = new String[] { "Show courses", "Add news", "Write order", "Show messages",
                    "Write message", "News", "Change password" };
            printOptions(options);

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

            if (courses.size() == 0) {
                printResult("Empty!");
                return;
            }

            printInfo("Choose course!");

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
            String[] options = new String[] { "Show course info", "Show course files", "Show teacher info", "Show students",
                    "Upload file", "Put marks" };
            printOptions(options);

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    showCourseInfo(course);
                    break;
                case "2":
                    showFiles(course);
                    break;
                case "3":
                    showTeacherInfo(course.getTeacher());
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
        printInfo("Type title of file!");
        String title = SCANNER.nextLine();

        printInfo("Type text of file!");
        String text = SCANNER.nextLine();

        File file = teacher.createFile(title, text);

        course.uploadFile(file);
        printResult("File uploaded!");
        LOGGER.uploadFile(teacher, course, file);

        DATABASE.save();
    }

    /* Put marks */
    private void putMarks(Teacher teacher, Course course) {
        printInfo("Type login of student!");
        String login = SCANNER.nextLine();

        printInfo("Choose mode of mark!");
        MarkMode[] modes = DATABASE.getMarkModes();
        printModes(modes);
        int index = SCANNER.nextInt();
        MarkMode mode = modes[index-1];

        printInfo("Type score to add for student!");
        double delta = SCANNER.nextDouble();

        SCANNER.nextLine();

        try {
            teacher.putMark(login, course, mode, delta);
            printResult("Mark put!");

            DATABASE.save();
        }
        catch (NotCurrentCourse e) {
            printError(e.getMessage());
        }
    }

    /* --------------------------------------------------- Manager -------------------------------------------------- */
    private void innerSession(Manager manager) {
        String answer = "";

        while (!answer.equals(BACK)) {
            String[] options = new String[] { "Add news", "Write order", "Show messages", "Write message",
                    "News", "Change password" };
            printOptions(options);

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    writeNews(manager);
                    break;
                case "2":
                    sendOrder(manager);
                    break;
                case "3":
                    showMessages(manager);
                    break;
                case "4":
                    writeMessage(manager);
                    break;
                case "5":
                    showNewses();
                    break;
                case "6":
                    changePassword(manager);
                    break;
            }
        }
    }

    /* -------------------------------------------------- Executor -------------------------------------------------- */
    private void innerSession(Executor executor) {
        String answer = "";

        while (!answer.equals(BACK)) {
            String[] options = new String[] { "New orders", "Accepted orders", "Rejected orders", "Finished orders",
                    "Show messages", "Write messages", "News", "Change password" };
            printOptions(options);

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
                printResult("Empty!");
                return;
            }

            for (int i = 0; i < orders.size(); ++i) {
                System.out.println(i+1 + ". " + orders.get(i));
            }

            printInfo("Choose order!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showOrder(executor, orders.get(index-1));
        }
    }

    private void showOrder(Executor executor, Order order) {
        String answer;

        Employee sender = order.getSender();

        System.out.println(String.format("Order: %s [%s]", order.getTitle(), order.getStatus()));
        System.out.println(String.format("Text: %s", order.getText()));;
        System.out.println(String.format("Sender: %s [%s]", sender.getFullName(), sender.getLogin()));;
        System.out.println(String.format("Date: %s", order.getTimestamp()));

        if (order.getStatus() == OrderStatus.NEW) {
            printInfo("This is new order! Do you accept or reject?");
            String[] options = new String[] { "Accept order", "Reject order" };
            printOptions(options);

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    executor.changeOrderStatus(order, OrderStatus.PENDING);
                    printResult("Order accepted! (now pending)");
                    break;
                case "2":
                    executor.changeOrderStatus(order, OrderStatus.REJECTED);
                    printResult("Order rejected!");
                    break;
            }
        }
        else if (order.getStatus() == OrderStatus.PENDING) {
            printInfo("This is pending order! Do you finished it?");
            String[] options = new String[] { "Finish order", "Reject order" };
            printOptions(options);

            answer = SCANNER.nextLine();

            switch (answer) {
                case "1":
                    executor.changeOrderStatus(order, OrderStatus.FINISHED);
                    printResult("Order finished!");
                    break;
                case "2":
                    executor.changeOrderStatus(order, OrderStatus.REJECTED);
                    printResult("Order rejected!");
                    break;
            }
        }
        else if (order.getStatus() == OrderStatus.REJECTED) {
            printInfo("This is rejected order! Do you accept it?");
            String[] options = new String[] { "Accept order"};
            printOptions(options);

            answer = SCANNER.nextLine();

            if ("1".equals(answer)) {
                executor.changeOrderStatus(order, OrderStatus.PENDING);
                printResult("Order accepted! (now pending)");
            }
        }
        else {
            printInfo("This is finished order! Do you undone it?");
            String[] options = new String[] { "Undone order"};
            printOptions(options);

            answer = SCANNER.nextLine();

            if ("1".equals(answer)) {
                executor.changeOrderStatus(order, OrderStatus.PENDING);
                printResult("Order accepted! (now pending)");
            }
        }

        DATABASE.save();
    }

    /* --------------------------------------------------- Other ---------------------------------------------------- */

    /* Show course info */
    private void showCourseInfo(Course course) {
        Teacher teacher = course.getTeacher();

        System.out.println(String.format("Course name: %s", course.getName()));
        System.out.println(String.format("Credit number: %d", course.getCreditNumber()));
        System.out.println(String.format("Teacher: %s [%s]", teacher.getFullName(), teacher.getLogin()));

        await();
    }

    /* Show files */
    private void showFiles(Course course) {
        List<File> files = course.getFiles();

        while (true) {
            for (int i = 0; i < files.size(); ++i) {
                System.out.println(i+1 + ". " + files.get(i).getTitle());
            }

            if (files.size() == 0) {
                printResult("Empty!");
                return;
            }

            printInfo("Choose file!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showFile(files.get(index-1));
        }
    }

    private void showFile(File file) {
        System.out.println(String.format("Title: %s", file.getTitle()));
        System.out.println(String.format("Text: %s", file.getText()));
        System.out.println(String.format("Created by %s", file.getCreator()));

        await();
    }

    /* Show teacher info */
    private void showTeacherInfo(Teacher teacher) {
        System.out.println(String.format("Full name: %s [%s]", teacher.getFullName(), teacher.getLogin()));
        System.out.println(String.format("Birth date: %s", teacher.getBirthDate()));
        System.out.println(String.format("Gender: %s", teacher.getGender()));
        System.out.println(String.format("Phone number: %s", teacher.getPhoneNumber()));
        System.out.println(String.format("Email: %s", teacher.getEmail()));
        System.out.println(String.format("Faculty: %s", teacher.getFaculty()));
        System.out.println(String.format("Position: %s", teacher.getPosition()));

        await();
    }

    /* Show students */
    private void showStudents(Course course) {
        List<Student> students = course.getStudents();

        if (students.size() == 0) {
            printResult("Empty!");
            return;
        }

        for (Student student: students) {
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
                printResult("Empty!");
                return;
            }

            for (int i = 0; i < messages.size(); ++i) {
                System.out.println(i+1 + ". " + messages.get(i).getTitle());
            }

            printInfo("Choose message!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showMessage(messages.get(index-1));
        }
    }

    private void showMessage(Message message) {
        Employee sender = message.getSender();

        System.out.println(String.format("Title: %s", message.getTitle()));
        System.out.println(String.format("Text: %s", message.getText()));
        System.out.println(String.format("Sender: %s [%s]", sender.getFullName(), sender.getLogin()));
        System.out.println(String.format("Date: %s", message.getTimestamp()));

        await();
    }

    /* Write message */
    private void writeMessage(Messaging messaging) {
        printInfo("Type title of message!");
        String title = SCANNER.nextLine();

        printInfo("Type text of message!");
        String text = SCANNER.nextLine();

        printInfo("Type login of target!");
        String login = SCANNER.nextLine();

        User user = DATABASE.getUser(login);

        if (user instanceof Messaging) {
            Messaging target = (Messaging) user;
            messaging.sendMessage(title, text, target);
            printResult("Message sent!");

            DATABASE.save();
        }
        else {
            printError("Login is not found or not have messages");
        }
    }

    /* Show newses */
    private void showNewses() {
        while (true) {
            List<News> newses = DATABASE.getNews();

            if (newses.size() == 0) {
                printResult("Empty!");
                return;
            }

            for (int i = 0; i < newses.size(); ++i) {
                System.out.println(i+1 + ": " + newses.get(i).getTitle());
            }

            printInfo("Choose news!");

            String answer = SCANNER.nextLine();

            if (answer.equals(BACK))
                break;

            int index = Integer.parseInt(answer);

            showNews(newses.get(index-1));
        }
    }

    private void showNews(News news) {
        Employee sender = news.getSender();

        System.out.println(String.format("Title: %s [%s]", news.getTitle(), news.getFaculty()));
        System.out.println(String.format("Text: %s", news.getText()));
        System.out.println(String.format("Sender: %s [%s]", sender.getFullName(), sender.getLogin()));
        System.out.println(String.format("Date: %s", news.getTimestamp()));

        await();
    }

    /* Write news */
    private void writeNews(ManagingNews sender) {
        printInfo("Type title of news!");
        String title = SCANNER.nextLine();

        printInfo("Type text of news!");
        String text = SCANNER.nextLine();

        News news = sender.createNews(title, text);
        DATABASE.addNews(news);
        DATABASE.save();

        printResult("News created!");
        LOGGER.writeNews(sender, news);
    }

    /* Change password */
    private void changePassword(User user) {
        printInfo("Type current password!");
        String currentPassword = SCANNER.nextLine();

        printInfo("Type new password!");
        String newPassword = SCANNER.nextLine();

        printInfo("Type new password again!");
        String newPassword2 = SCANNER.nextLine();

        if (user.checkCredentials(user.getLogin(), currentPassword) && newPassword.equals(newPassword2)) {
            user.setPassword(newPassword);
            printResult("Password changed!");

            DATABASE.save();
        }
        else {
            printError("Credentials not match!");
        }
    }

    /* Send order */
    private void sendOrder(SendingOrders sender) {
        printInfo("Type title of order!");
        String title = SCANNER.nextLine();

        printInfo("Type text of order!");
        String text = SCANNER.nextLine();

        printInfo("Type login of executor!");
        String login = SCANNER.nextLine();

        User user = DATABASE.getUser(login);

        if (user instanceof Executor) {
            Executor executor = (Executor) user;
            Order order = sender.sendOrder(title, text, executor);
            DATABASE.save();

            printResult("Order sent!");
            LOGGER.sendOrder(sender, order, executor);
        }
        else {
            printError("Login is not found or not have orders");
        }

    }

    private void await() {
        String answer = "";

        while (!answer.equals(BACK)) {
            answer = SCANNER.nextLine();
        }
    }

    private void printInfo(String info) {
        PRINTER.printInfo(info);
    }

    private void printResult(String result) {
        PRINTER.printResult(result);
    }

    private void printError(String error) {
        PRINTER.printError(error);
    }

    private void printOptions(String[] options) {
        PRINTER.printOptions(options);
    }

    private void printFaculties(Faculty[] faculties) {
        PRINTER.printFaculties(faculties);
    }

    private void printDegrees(Degree[] degrees) {
        PRINTER.printDegrees(degrees);
    }

    private void printPositions(TeacherPosition[] positions) {
        PRINTER.printPositions(positions);
    }

    private void printModes(MarkMode[] modes) {
        PRINTER.printModes(modes);
    }
}
