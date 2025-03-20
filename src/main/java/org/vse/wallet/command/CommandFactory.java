package org.vse.wallet.command;

public interface CommandFactory {
    Command createCommand(CommandType cmdType);
}
