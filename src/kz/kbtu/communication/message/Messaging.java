package kz.kbtu.communication.message;

import java.util.List;

public interface Messaging {
    List<Message> getMessages();
    void sendMessage(String title, String text, Messaging target);
}
