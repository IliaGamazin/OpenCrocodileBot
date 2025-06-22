package services.messages;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface Builder {
    void setChatId(long id);
    void setText(String text);
    void setReplyMarkup(ReplyKeyboard markup);
}
