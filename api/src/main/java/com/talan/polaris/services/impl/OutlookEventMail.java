package com.talan.polaris.services.impl;
import com.talan.polaris.dto.CalendarRequest;
import org.springframework.mail.javamail.JavaMailSender;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import static com.talan.polaris.constants.EmailEventConstants.*;

/**
 * OutlookEventMail.
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */

public class OutlookEventMail {

    private JavaMailSender mailSender;

    public OutlookEventMail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     *
     * @param fromEmail
     * @param calendarRequest
     * @param organizer
     * @param pointName
     * @throws Exception
     */
    public void sendCalendarInvite(String fromEmail, CalendarRequest calendarRequest,String organizer,String pointName) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.addHeaderLine(HEADER_LINE1);
        mimeMessage.addHeaderLine(HEADER_LINE2);
        mimeMessage.addHeaderLine(HEADER_LINE3);
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
        mimeMessage.setSubject(calendarRequest.getSubject());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");

        StringBuilder builder = new StringBuilder();
        builder.append(FIRST_PART_EMAIL+ "TZID:"+ ZoneId.systemDefault()+"\n" + "END:VTIMEZONE\n" + "BEGIN:VEVENT\n"+
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + calendarRequest.getToEmail() + "\n" +
                "ORGANIZER;CN="+organizer+":MAILTO:" + fromEmail + "\n" +
                "DESCRIPTION;LANGUAGE=en-US:" + calendarRequest.getBody() + "\n" +
                "UID:"+calendarRequest.getUid()+"\n" +
                "SUMMARY;LANGUAGE=en-US:"+pointName+"\n" +
                "DTSTART:" + formatter.format(calendarRequest.getMeetingStartTime()).replace(" ", "T") + "\n" +
                "DTEND:" + formatter.format(calendarRequest.getMeetingEndTime()).replace(" ", "T") + "\n" +
                SECOND_PART_EMAIL);
            MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        messageBodyPart.setDataHandler(new DataHandler(
                new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));/**/
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        mimeMessage.setContent(multipart);
        mailSender.send(mimeMessage);
    }
}