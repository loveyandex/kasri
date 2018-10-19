package com.amin.notify;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class NotifyBot extends TelegramLongPollingBot {

    private static NotifyBot notifyBot = new NotifyBot();

    public static NotifyBot getNotifyBot() {
        return notifyBot;
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {
            sendMessage(new SendMessage()
                    .setChatId(update.getMessage().getChatId()).
                            setText(update.getMessage().getText()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "AminMusic";
    }

    @Override
    public String getBotToken() {
        return "471596579:AAHa2Fbmv5BWcCvzQZ_DiE-a1x44YiEySzc";
    }

}
