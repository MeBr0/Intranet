package kz.kbtu.study.course;

import kz.kbtu.auth.main.Student;
import kz.kbtu.auth.main.Teacher;
import kz.kbtu.study.File;
import kz.kbtu.study.Marks;
import kz.kbtu.study.throwable.DeadlinePassed;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {
    private String name;
    private int creditNumber;
    private Teacher teacher;
    private List<Student> students;
    private List<File> files;
    private Map<String, Marks> marks;
    private Map<String, CourseStatus> statuses;

    {
        students = new ArrayList<>();
        files = new ArrayList<>();
        marks = new HashMap<>();
        statuses = new HashMap<>();
    }

    public Course(String name, int creditNumber, Teacher teacher) {
        this.name = name;
        this.creditNumber = creditNumber;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public int getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(int creditNumber) {
        this.creditNumber = creditNumber;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public boolean removeStudent(String login) {
        for (Student student: students) {
            if (student.getLogin().equals(login)) {
                this.students.remove(student);

                return true;
            }
        }

        return false;
    }

    public List<File> getFiles() {
        return files;
    }

    public void uploadFile(File file) {
        this.files.add(file);
    }

    public boolean removeFile(String title) {
        for (File file: files) {
            if (file.getTitle().equals(title)) {
                files.remove(file);
                return true;
            }
        }

        return false;
    }

    public Map<String, Marks> getMarks() {
        return marks;
    }

    public Marks getMarks(String login) {
        return marks.get(login);
    }

    public void putMarks(String login, MarkMode mode, double score) throws DeadlinePassed {
        switch (mode) {
            case ATTESTATION1:
                getMarks(login).updateAttestation1(score);
                break;
            case ATTESTATION2:
                getMarks(login).updateAttestation2(score);
                break;
            case FINAL:
                getMarks(login).updateFinale(score);
                updateStatus(login, CourseStatus.FINISHED);
                break;
        }
    }

    public void openMarks(String login) {
        marks.put(login, new Marks());
    }

    public Map<String, CourseStatus> getStatuses() {
        return statuses;
    }

    public CourseStatus getStatus(String login) {
        return this.statuses.get(login);
    }

    public void updateStatus(String login, CourseStatus status) {
        if (this.statuses.get(login) == null) {
            this.statuses.put(login, status);
        }
        else {
            this.statuses.replace(login, status);
        }
    }

    public void print() {
        System.out.println(String.format("Course name: %s", name));
        System.out.println(String.format("Credit number: %d", creditNumber));
        System.out.println(String.format("Teacher: %s [%s]", teacher.getFullName(), teacher.getLogin()));
    }

    @Override
    public String toString() {
        return String.format("Course { name: %s, creditNumber: %d, teacher: %s", name, creditNumber, teacher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return creditNumber == course.creditNumber &&
                name.equals(course.name) &&
                teacher.equals(course.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, creditNumber, teacher);
    }
}
