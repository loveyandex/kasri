package com.amin.notify;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class MainApp {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(NotifyBot.getNotifyBot());
        } catch (TelegramApiRequestException e) {

            e.printStackTrace();
        }
    }
}
