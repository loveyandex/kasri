package test;

import com.opencsv.CSVWriter;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * is created by aMIN on 7/16/2018 at 21:10
 */
public class Jsoup {
    private static final String STRING_ARRAY_SAMPLE = "./dollar-euro.csv";

    public static void main(String[] args) throws IOException {
        String url = "https://www.google.com/search?ei=L8pMW5yXPIOR6ASV76eACQ&q=5k+dollar+to++euro&oq=5k+dollar+to++euro&gs_l=psy-ab.3...8493.11670.0.11925.11.11.0.0.0.0.170.1095.0j7.7.0....0...1c.1.64.psy-ab..4.2.322...0j0i7i30k1j0i30k1j0i5i30k1j0i8i7i30k1.0.SqOVz7BPUgg";

        Connection connect = org.jsoup.Jsoup.connect(url);
        Document document = connect.get();
        Element body = document.body();
        Element knowledge = body.getElementById("knowledge-currency__tgt-amount");
        String eu = knowledge.text();

        try (
                Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE), StandardCharsets.UTF_8, StandardOpenOption.APPEND);

                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            csvWriter.writeNext(new String[]{eu});
        }

        Dia.infoBox("5K$ to Euro", eu);


    }
}


class Dia {

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}