package kz.kbtu.study.course;

import kz.kbtu.auth.main.Student;
import kz.kbtu.auth.main.Teacher;
import kz.kbtu.study.File;
import kz.kbtu.study.Marks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void setMarks(Map<String, Marks> marks) {
        this.marks = marks;
    }

    public Map<String, CourseStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Map<String, CourseStatus> statuses) {
        this.statuses = statuses;
    }
}
