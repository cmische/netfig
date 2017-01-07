package io.cmische.github.netfig;

//import javafx.application.Platform;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;

public class OutputConsole extends OutputStream  {
    private TextArea console;

    OutputConsole(TextArea console) {
        this.console = console;
    }

    private void appendText(String string) {
        Platform.runLater(() -> {
            console.appendText(string);
            //console.positionCaret(console.getLength());
        });

    }

    public void write(int b) throws IOException {
        appendText(String.valueOf((char)b));
    }
}
