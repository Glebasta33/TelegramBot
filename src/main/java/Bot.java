import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    // возврат имени бота
    @Override
    public String getBotUsername() {
        return "MyTestBot";
    }

    // токен от BotFather
    @Override
    public String getBotToken() {
        return TokenStorage.API_TOKEN_TELEGRAM;
    }

    // обновление получаемых сообщений
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Введите название города на русском или английском языке");
                    break;
                case "/settings":
                    sendMsg(message, "Для русского языка введите: /ru \nFor english enter: /eng");
                    break;
                case "/ru":
                    Weather.lang = "ru";
                    break;
                case "/eng":
                    Weather.lang = "eng";
                    break;
                default:
                    try {
                        sendMsg(message, Weather.getWeather(message.getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        sendMsg(message, "Введите корректное название города.");
                    }
            }
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true); // for many variables answers
        sendMessage.setChatId(message.getChatId().toString()); // для получения id чата
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text); // запускает отправку сообщения
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); // создание клавиатуры
        sendMessage.setReplyMarkup(replyKeyboardMarkup); // разметка для клавиатуры (связывает сообщение с клавиатурой)
        replyKeyboardMarkup.setSelective(true); // выводить клавиатуру всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true); // автоматическа подгонять размер клавиатуры
        replyKeyboardMarkup.setOneTimeKeyboard(false); // не скрывать клавиатуру после нажатия

        // список кнопок-строк
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // добавление кнопок в первую строку
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/settings"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList); // уставнока кнопок на клавиатуру

    }
}
