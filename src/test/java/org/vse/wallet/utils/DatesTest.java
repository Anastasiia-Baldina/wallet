package org.vse.wallet.utils;
import org.junit.jupiter.api.Test;
import org.vse.wallet.ui.exception.IllegalFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DatesTest {
    @Test
    public void testFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String expected = dateFormat.format(date);
        String actual = Dates.format(date);
        assertEquals(expected, actual);
    }

    @Test
    public void testParse() throws ParseException {
        String str = "01.01.2022";
        Date expected = new SimpleDateFormat("dd.MM.yyyy").parse(str);
        Date actual = Dates.parse(str);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseInvalidDate() {
        String str = "invalid date";
        assertThrows(IllegalFormatException.class, () -> Dates.parse(str));
    }
}



