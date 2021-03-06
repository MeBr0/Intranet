package kz.kbtu.study;

import java.io.Serializable;
import java.util.Objects;

public class File implements Serializable {
    private String title;
    private String text;
    private String creator;

    public File(String title, String text, String creator) {
        this.title = title;
        this.text = text;
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCreator() {
        return creator;
    }

    public final void print() {
        System.out.println(String.format("Title: %s", title));
        System.out.println(String.format("Text: %s", text));
        System.out.println(String.format("Created by %s", creator));
    }

    @Override
    public String toString() {
        return String.format("File { title: %s, text: %s, creator: %s }", title, text, creator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;
        File file = (File) o;
        return title.equals(file.title) &&
                text.equals(file.text) &&
                creator.equals(file.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, text, creator);
    }
}
