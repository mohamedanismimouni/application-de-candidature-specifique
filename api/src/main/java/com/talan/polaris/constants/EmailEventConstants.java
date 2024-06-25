package com.talan.polaris.constants;

/**
 * CommonConstants.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public abstract class EmailEventConstants {
    public static final String FIRST_PART_EMAIL                              ="BEGIN:VCALENDAR\n" + "METHOD:REQUEST\n" + "PRODID:Microsoft Exchange Server 2010\n" + "VERSION:2.0\n" + "BEGIN:VTIMEZONE\n" ;
    public static final String SECOND_PART_EMAIL                             = "CLASS:PUBLIC\n" + "PRIORITY:5\n" + "DTSTAMP:20200922T105302Z\n" + "TRANSP:OPAQUE\n" + "STATUS:CONFIRMED\n" + "SEQUENCE:$sequenceNumber\n" + "LOCATION;LANGUAGE=en-US:Microsoft Teams Meeting\n" + "BEGIN:VALARM\n" + "DESCRIPTION:REMINDER\n" + "TRIGGER;RELATED=START:-PT15M\n" + "ACTION:DISPLAY\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR";
    public static final String HEADER_LINE1                                 ="method=REQUEST";
    public static final String HEADER_LINE2                                 ="charset=UTF-8";
    public static final String HEADER_LINE3                                  ="component=VEVENT";






    private EmailEventConstants() {

    }

}
