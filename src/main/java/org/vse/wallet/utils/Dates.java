package org.vse.wallet.utils;

import org.vse.wallet.ui.exception.IllegalFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Dates {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private Dates() {
    }

    public static String format(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static Date parse(String str) {
        try {
            return DATE_FORMAT.parse(str);
        } catch (ParseException e) {
            throw new IllegalFormatException("Expected date in 'dd.mm.yyyy' format but was '" + str + "'");
        }
    }
}
