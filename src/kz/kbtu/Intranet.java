package kz.kbtu;

import kz.kbtu.auth.base.User;

import java.util.Scanner;

public class Intranet {

    private Database database;
    private final Scanner SCANNER;

    {
        database = new Database();
        database.load();

        SCANNER = new Scanner(System.in);
    }

    public void begin() {
//        System.out.println(database.getUsers());
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
                beginSession(user);
            }
            else {
                System.out.println("Invalid credentials! Try again!");
            }
        }

        database.save();
    }

    private void beginSession(User user) {
        System.out.println("Begin as " + user.getClass());
    }
}
