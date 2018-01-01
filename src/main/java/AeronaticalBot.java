import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.me.HTML;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by ABOLFZL on 10/27/2017.
 */
public class AeronaticalBot extends TelegramLongPollingBot {
    int jj=16;
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();

        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("/getdata"+jj);
            if (update.getMessage().getText().equals("/getdata"+jj)) {
                try {
                    jj++;
                    String fs = System.getProperty("user.dir") + "\\assets\\Data"+jj+".dat";
                    File file = new File(fs);

                    if (file.createNewFile()) {
                        FileWriter fw = new FileWriter(file);
                        fw.write(getData());
                        fw.flush();
                        fw.close();
                    } else {
                        FileWriter fw = new FileWriter(file);
                        fw.write(getData());
                        fw.flush();
                        fw.close();
                    }
                    Scanner sc = new Scanner(file);
//                    List<String> lines = new ArrayList<String>();
                    String dataline = "";
                    int i = 0;
                    while (sc.hasNextLine()) {
                        dataline += sc.nextLine();
                        sendMessage.setChatId(update.getMessage().getChatId())
                                .setText(dataline);
                        if (!dataline.equals("") && (i % 20) == 0) {sendMessage(sendMessage);
                        dataline="";}
                        i++;
                    }
                    if (!dataline.equals(""))sendMessage(sendMessage);
                    sendMessage.setChatId(update.getMessage().getChatId())
                            .setText("these data\uD83D\uDC46\uD83D\uDC46\uD83D\uDC46\uD83D\uDC46 \n" +
                                    " is coming from this website::\n " +
                                    "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47"
                                    +getUrlQuerry());
                    sendMessage(sendMessage);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                sendMessage.setChatId(update.getMessage().getChatId())
                        .setText("please press /getdata command");
                try {
                    sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    private String getData() {
        HtmlElement o = null;
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(getUrlQuerry());
            o = ((HtmlElement) page.
                    getByXPath("/html/body/pre[1]").get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o.getTextContent();
    }

    private String getUrlQuerry() {
        String[] ss = new String[]{"40785", "40754", "71909", "71109", "71815"
                , "71913", "72235", "72340", "70350", "71802"};
        return HTML.setQuery("mideast", "TEXT%3ALIST", String.valueOf(2017-jj+1),
                "10", "2612", "3112", ss[((int) (1))]);
    }

    public String getBotUsername() {
        return "aeronauticalBotBotBotBotBotBot";
    }

    public String getBotToken() {
        return "424901891:AAHO3mawU81YDVPwSeBdQVkoWtaaWxNFNxk";
    }
}
