package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePasswordController  {

    @FXML
    private TextField newUserName;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Label errorLabel;


    @FXML
    private void updatePassword(ActionEvent event)throws Exception
    {

        if( !newPassword.getText().equals(confirmPassword.getText()))
        {
            errorLabel.setText("Please retype the password properly");
        }

        else {
            errorLabel.setText("");
            PasswordManager.updatePassword(newUserName.getText(), newPassword.getText());

            Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("LoginSuccess.fxml"));
            stage.setScene(new Scene(root) );
            stage.show();

        }


    }
}
