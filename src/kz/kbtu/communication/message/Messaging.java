package kz.kbtu.communication.message;

import java.util.List;

public interface Messaging {
    List<Message> getMessages();
    Message readMessage(String title);
    void sendMessage(Message message, Messaging target);
    Message createMessage(String title, String text);
}
