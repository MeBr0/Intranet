package kz.kbtu.util;

import kz.kbtu.auth.type.Degree;
import kz.kbtu.auth.type.Faculty;
import kz.kbtu.auth.type.TeacherPosition;
import kz.kbtu.study.course.MarkMode;

public class Printer {

    private static Printer printer = null;

    private Printer() {

    }

    public static Printer getInstance() {
        if (printer == null) {
            printer = new Printer();
        }

        return printer;
    }

    private final char INFO = '-';
    private final char RESULT = '!';
    private final char ERROR = '!';

    public void printInfo(String info) {
        System.out.println(INFO + " " + info);
    }

    public void printResult(String result) {
        System.out.println(RESULT + " " + result);
    }

    public void printError(String error) {
        System.err.println(ERROR + " " + error);
    }

    public void printOptions(String[] options) {
        for (int i = 0; i < options.length; ++i) {
            System.out.println(i+1 + ". " + options[i]);
        }
    }

    public void printFaculties(Faculty[] faculties) {
        for (int i = 0; i < faculties.length; ++i) {
            System.out.println(i+1 + ". " + faculties[i]);
        }
    }

    public void printDegrees(Degree[] degrees) {
        for (int i = 0; i < degrees.length; ++i) {
            System.out.println(i+1 + ". " + degrees[i]);
        }
    }

    public void printPositions(TeacherPosition[] positions) {
        for (int i = 0; i < positions.length; ++i) {
            System.out.println(i+1 + ". " + positions[i]);
        }
    }

    public void printModes(MarkMode[] modes) {
        for (int i = 0; i < modes.length; ++i) {
            System.out.println(i+1 + ". " + modes[i]);
        }
    }

}
