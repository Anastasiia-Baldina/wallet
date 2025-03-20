package org.vse.wallet.ui;

import org.vse.wallet.data.Identifiable;
import org.vse.wallet.ui.editor.EditorType;

public interface EditorFactory {
    <T extends Identifiable> Editor<T> createEditor(EditorType type);
}
