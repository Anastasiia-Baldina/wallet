package org.vse.wallet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vse.wallet.command.Command;
import org.vse.wallet.command.CommandFactory;
import org.vse.wallet.command.CommandType;
import org.vse.wallet.command.Executor;
import org.vse.wallet.ui.UserInterface;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class ApplicationTest {
    @Mock
    private UserInterface ui;
    @Mock
    private Executor executor;
    @Mock
    private CommandFactory commandFactory;
    @Mock
    private Command command;

    private Application application;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        application = new Application(ui, executor, commandFactory);
    }

    @Test
    public void testStart() {
        List<String> menu = Stream.of(CommandType.values()).map(CommandType::description).collect(Collectors.toList());
        menu.add("Exit");
        when(ui.selectOption("Select command", menu))
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(menu.size() - 1);
        when(commandFactory.createCommand(CommandType.values()[0])).thenReturn(command);
        when(commandFactory.createCommand(CommandType.values()[1])).thenReturn(command);

        application.start();

        verify(ui, times(3)).selectOption("Select command", menu);
        verify(commandFactory, times(2)).createCommand(any(CommandType.class));
        verify(executor, times(2)).execute(command);
        verify(ui, times(2)).pause();
        verify(ui).printLine("Bye !");
    }
}


