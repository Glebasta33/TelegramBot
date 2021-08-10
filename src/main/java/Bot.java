import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    // возврат имени бота
    @Override
    public String getBotUsername() {
        return "MyTestBot";
    }

    // токен от BotFather
    @Override
    public String getBotToken() {
        return TokenStorage.API_TOKEN;
    }

    // обновление получаемых сообщений
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Чем могу помочь ?");
                    break;
                case "/settings":
                    sendMsg(message, "Что нужно настроить ?");
                    break;
                default:
            }
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true); // for many variables answers
        sendMessage.setChatId(message.getChatId().toString()); // для получения id чата
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
