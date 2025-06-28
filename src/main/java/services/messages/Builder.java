package services.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface Builder {
    void setChatId(long id);
    void setText(String text);
    void setReplyMarkup(ReplyKeyboard markup);
    void addButtonRow(String text, String callback);

    SendMessage build();
}
