package kz.kbtu.util;

import kz.kbtu.auth.base.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Serializer {

    public static <T> ArrayList<T> load(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {

            Object object = inputStream.readObject();

            return (ArrayList<T>) object;
        }
        catch (IOException e) {
            System.err.println(fileName + ": IOException");
        }
        catch (ClassNotFoundException e) {
            System.err.println(fileName + ": ClassNotFoundException");
        }

        return null;
    }

    public static <T> void save(List<User> object, String fileName) {
        try (ObjectOutputStream oot = new ObjectOutputStream(new FileOutputStream(fileName));) {
            oot.writeObject(object);

            oot.flush();
        }
        catch (IOException e) {
            System.err.println(fileName + ": IOException");
        }
    }
}
