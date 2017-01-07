package io.cmische.github.netfig;


import io.cmische.github.netfig.ssh.Credentials;
import io.cmische.github.netfig.ssh.SSH;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML // GUI handles declared in and injected by FXMLLoader
    public TextArea textOut;
    private OutputConsole outputConsole;
    @FXML // GUI handles declared in and injected by FXMLLoader
    public TextArea textIn;
    private InputConsole inputConsole;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField ipAddress;
    @FXML
    private CheckBox validateKnownHost;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputConsole = new InputConsole(textIn);
        outputConsole = new OutputConsole(textOut);
    }

    @FXML
    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            inputConsole.q.add(textIn.getText());
            textIn.setText("");
            textIn.positionCaret(0);
        }
    }

    @FXML
    private void onclick_btn_ssh(/*ActionEvent event*/) {
        Credentials credentials = new Credentials(ipAddress.getText(),username.getText(),password.getText(),validateKnownHost.isSelected());
        SSH.login(credentials, inputConsole, outputConsole);
    }

}
