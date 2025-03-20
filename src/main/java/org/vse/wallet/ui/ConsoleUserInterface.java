package org.vse.wallet.ui;

import org.vse.wallet.ui.exception.IllegalFormatException;
import org.vse.wallet.utils.Asserts;
import org.vse.wallet.utils.Dates;

import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {
    private final PrintStream writer;
    private final Scanner reader;

    public ConsoleUserInterface() {
        writer = System.out;
        reader = new Scanner(System.in);
    }

    public ConsoleUserInterface(PrintStream writer, Scanner reader) {
        this.writer = writer;
        this.reader = reader;
    }

    @Override
    public void print(String txt) {
        writer.print(txt);
    }

    @Override
    public void printLine(String txt) {
        writer.println(txt);
    }

    @Override
    @Nonnull
    public Date readDate(String prompt) {
        var line = readString(prompt + " (dd.mm.yyyy)");
        return Dates.parse(line);
    }

    @Override
    public int readInt(String prompt) {
        var line = readString(prompt);
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new IllegalFormatException("Expected integer but was '" + line + "'");
        }
    }

    @Override
    public long readLong(String prompt) {
        var line = readString(prompt);
        try {
            return Long.parseLong(line);
        } catch (NumberFormatException e) {
            throw new IllegalFormatException("Expected long but was '" + line + "'");
        }
    }

    @Override
    public String readString(String prompt) {
        print(prompt + ": ");
        return reader.nextLine();
    }

    @Override
    public int selectOption(String prompt, List<String> options) {
        Asserts.notEmpty(options, "options");
        if(options.size() == 1) {
            return 0;
        }
        int val;
        do {
            printLine(prompt + ":");
            for (int i = 0; i < options.size(); i++) {
                printLine(i + "\t" + options.get(i));
            }
            val = readInt("Type selected [0 .. " + (options.size() - 1) + "]");
        } while (val < 0 || val >= options.size());

        return val;
    }

    @Override
    public void pause() {
        print("*** Press Enter to continue...");
        reader.nextLine();
    }
}
