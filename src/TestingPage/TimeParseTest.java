package TestingPage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// reference : https://leap.hcldoc.com/help/topic/SSS28S_8.2.1/XFDL_Specification/i_xfdl_r_formats_en_NZ.html

public class TimeParseTest {
    public static void main(String[] args) throws ParseException {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse("18:09:01");
        LocalTime time2 = LocalTime.parse(LocalTime.now().format(timeFormat));
        System.out.println(time2);
    }
}

class TestDate{
    public static void main(String[] args) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/MM/yyyy");
        System.out.println(LocalDate.parse("12/03/2022",dateFormat));
    }
}