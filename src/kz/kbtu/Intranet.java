package kz.kbtu;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.Admin;
import kz.kbtu.auth.main.Student;
import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.util.Logger;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Intranet {

    private Database database;
    private final Scanner SCANNER;
    private final Logger LOGGER;

    {
        database = new Database();
        database.load();

        SCANNER = new Scanner(System.in);
        LOGGER = new Logger();
    }

    public void begin() {
        System.out.println(database.getUsers());
        System.out.println("Welcome to Intranet system!");

        int i = 0;

        while (i < 3) {
            System.out.println("Enter login please!");
            String login = SCANNER.nextLine();

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
    }

    private void adminSession(Admin admin) {
        LOGGER.enterAdmin(admin);

        String answer = "";

        while (!answer.equals("quit")) {
            System.out.println("1. Add user");
            System.out.println("2. Remove user");
            System.out.println("3. Show users");

            answer = SCANNER.nextLine();

            if (answer.equals("1")) {
                adminAdd(admin);
            }
        }
    }

    private void adminAdd(Admin admin) {
        String answer = "";

        while (!answer.equals("quit")) {
            System.out.println("1. Add student");

            answer = SCANNER.nextLine();

            if (answer.equals("1")) {
                adminAddStudent(admin);
            }
        }
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
}
