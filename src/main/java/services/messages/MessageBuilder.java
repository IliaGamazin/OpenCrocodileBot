package services.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class MessageBuilder implements Builder{
    private long chatId;
    private String text;
    private ReplyKeyboard markup;
    @Override
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setReplyMarkup(ReplyKeyboard markup) {
        this.markup = markup;
    }

    public SendMessage build() {
        SendMessage res = new SendMessage();
        res.setChatId(chatId);
        res.setText(text);
        res.setReplyMarkup(markup);
        return res;
    }
}
