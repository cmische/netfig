package io.cmische.github.netfig;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


public class Controller{

    @FXML // GUI handles declared in and injected by FXMLLoader
    private TextArea custext;

    @FXML
    private void onclick_btn_ssh(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        custext.setText(custext.getText() + " Button!");
    }

    /*
    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        assert custext != null : "fx:id=\"custext\" was not injected: check your FXML file 'main.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    } */
}
