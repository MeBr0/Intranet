package kz.kbtu.auth.base;

import kz.kbtu.util.CustomHasher;

import java.io.Serializable;
import java.util.Objects;

public abstract class User extends Person implements Serializable {
    private String login;
    private String password;
    private String phoneNumber;
    private String email;

    {
        this.password = CustomHasher.getInstance().hash("Kbtu111");

//        System.out.println("This password " + this.password);
    }

    protected User(String login, String firstName, String lastName) {
        super(firstName, lastName);

        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = CustomHasher.getInstance().hash(password);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkCredentials(String login, String password) {
        String hashedPassword = CustomHasher.getInstance().hash(password);

        return this.login.equals(login) && this.password.equals(hashedPassword);
    }

    public void print() {
        System.out.println(String.format("Full name: %s [%s]", getFullName(), login));
        System.out.println(String.format("Birth date: %s", getBirthDate()));
        System.out.println(String.format("Gender: %s", getGender()));
        System.out.println(String.format("Phone number: %s", phoneNumber));
        System.out.println(String.format("Email: %s", email));
    }

    @Override
    public String toString() {
        return String.format("login: %s, %s", login, super.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return login.equals(user.login) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login);
    }
}
