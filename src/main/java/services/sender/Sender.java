package services.sender;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Sender {
    void send(SendMessage message);
    void send(AnswerCallbackQuery answer);
}
