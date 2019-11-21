package kz.kbtu.user;

import java.util.Date;

public abstract class User extends Person {
    private String login;
    private String password;
    private String phoneNumber;
    private String email;

    public User(String login, String password, String firstName, String lastName, Date birthDate, Gender gender) {
        super(firstName, lastName, birthDate, gender);

        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "User {" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                "} " + super.toString();
    }
}
