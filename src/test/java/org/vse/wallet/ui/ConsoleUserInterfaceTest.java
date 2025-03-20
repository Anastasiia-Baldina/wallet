package org.vse.wallet.ui;

import org.apache.commons.lang.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vse.wallet.utils.Dates;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConsoleUserInterfaceTest {
    @Mock
    private PrintStream writer;
    @Mock
    private Scanner reader;

    private ConsoleUserInterface consoleUserInterface;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleUserInterface = new ConsoleUserInterface(writer, reader);
    }

    @Test
    public void testPrint() {
        String txt = "Test";
        consoleUserInterface.print(txt);
        verify(writer).print(txt);
    }

    @Test
    public void testPrintLine() {
        String txt = "Test";
        consoleUserInterface.printLine(txt);
        verify(writer).println(txt);
    }

    @Test
    public void testReadDate() {
        Date date = new Date();
        when(reader.nextLine()).thenReturn(Dates.format(date));
        assertEquals(DateUtils.truncate(new Date(), Calendar.DATE), consoleUserInterface.readDate("Test"));
    }

    @Test
    public void testReadInt() {
        int val = 1;
        when(reader.nextLine()).thenReturn(String.valueOf(val));
        assertEquals(val, consoleUserInterface.readInt("Test"));
    }

    @Test
    public void testReadLong() {
        long val = 1L;
        when(reader.nextLine()).thenReturn(String.valueOf(val));
        assertEquals(val, consoleUserInterface.readLong("Test"));
    }

    @Test
    public void testReadString() {
        String txt = "Test";
        when(reader.nextLine()).thenReturn(txt);
        assertEquals(txt, consoleUserInterface.readString("Test"));
    }

    @Test
    public void testSelectOption() {
        List<String> options = Arrays.asList("Option1", "Option2");
        when(reader.nextLine()).thenReturn("0");
        assertEquals(0, consoleUserInterface.selectOption("Test", options));
    }

    @Test
    public void testPause() {
        consoleUserInterface.pause();
        verify(reader).nextLine();
    }
}

