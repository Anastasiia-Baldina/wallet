package org.vse.wallet;

import org.vse.wallet.command.CommandFactory;
import org.vse.wallet.command.CommandType;
import org.vse.wallet.command.Executor;
import org.vse.wallet.ui.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private final UserInterface ui;
    private final Executor executor;
    private final CommandFactory commandFactory;

    public Application(UserInterface ui, Executor executor, CommandFactory commandFactory) {
        this.ui = ui;
        this.executor = executor;
        this.commandFactory = commandFactory;
    }

    void start() {
        List<String> menu = new ArrayList<>(CommandType.values().length + 1);
        for (CommandType cmdType : CommandType.values()) {
            menu.add(cmdType.description());
        }
        menu.add("Exit");
        while (true) {
            try {
                int selected = ui.selectOption("Select command", menu);
                if (selected == menu.size() - 1) {
                    break;
                } else {
                    var cmdType = CommandType.values()[selected];
                    var cmd = commandFactory.createCommand(cmdType);
                    executor.execute(cmd);
                }
            } catch (Exception e) {
                ui.printLine("*** Error: " + e.getMessage());
                e.printStackTrace();
            }
            ui.pause();
        }
        ui.printLine("Bye !");
    }
}
