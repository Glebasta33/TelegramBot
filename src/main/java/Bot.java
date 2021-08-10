import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    // возврат имени бота
    @Override
    public String getBotUsername() {
        return "MyTestBot";
    }

    // токен от BotFather
    @Override
    public String getBotToken() {
        return null;
    }

    // обновление получаемых сообщений
    @Override
    public void onUpdateReceived(Update update) {

    }
}
