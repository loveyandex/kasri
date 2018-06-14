package com.amin.notify;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Notify {

    public static void sendSelfMsg(String tex)
    {
        try {
            NotifyBot.getNotifyBot().sendMessage(new SendMessage()
            .setChatId("145464749").setText(tex));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
