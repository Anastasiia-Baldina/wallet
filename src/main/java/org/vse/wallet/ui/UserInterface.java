package org.vse.wallet.ui;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

public interface UserInterface {
    void print(String txt);

    void printLine(String txt);

    @Nonnull
    Date readDate(String prompt);

    int readInt(String prompt);

    long readLong(String prompt);

    String readString(String prompt);

    int selectOption(String prompt, List<String> options);

    void pause();
}