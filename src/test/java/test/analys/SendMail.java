package test.analys;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendMail {
    public SendMail(String host, String Password, String from, String toAddress, String filePath, String filename, String setSubject, String bodyText) throws MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));

        message.setRecipients(Message.RecipientType.TO, toAddress);

        message.setSubject("amin naderi siktir");

        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setText("Here's the file");

        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(filename);

        messageBodyPart.setDataHandler(new DataHandler(source));

        messageBodyPart.setFileName("data");

        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        try {
            Transport tr = session.getTransport("smtps");
            tr.connect(host, from, Password);
            tr.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail Sent Successfully");
            tr.close();

        } catch (SendFailedException sfe) {

            System.out.println(sfe);
        }
    }

    public SendMail() {
        String host = "smtp.gmail.com";
        String Password = "kingamin1993";
        String from = "abfzlabfzl@gmail.com";
        String toAddress = "abbfzl@gmail.com";
        String filename = "C:/Users/AminAbvaal/Desktop/opt/ALL.dat";
        // Get system properties

    }


    public static void main(String args[]) {
        SendMail sm = new SendMail();
    }
}