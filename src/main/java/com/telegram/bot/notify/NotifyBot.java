package com.telegram.bot.notify;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class NotifyBot extends TelegramLongPollingBot {

    public static NotifyBot getNotifyBot() {
        return notifyBot;
    }
    private static NotifyBot notifyBot=new NotifyBot();

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "AminMusic";
    }

    @Override
    public String getBotToken() {
        return "471596579:AAHXzfiF6lRZmUcT5o8-IYtgMCg2ZmpHUMo";
    }

}
