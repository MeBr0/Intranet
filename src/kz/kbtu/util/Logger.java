package kz.kbtu.util;

import kz.kbtu.auth.base.User;
import kz.kbtu.auth.main.Admin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final String LOG = "log.txt";
    private DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");

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
}
