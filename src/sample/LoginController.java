package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class LoginController
{


    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;



    public void LoginAttempt(ActionEvent  event) throws Exception
    {
        if( PasswordManager.matches(userName.getText() ,password.getText())  )
        {
            errorLabel.setText("");
            Parent root = FXMLLoader.load(getClass().getResource("LoginSuccess.fxml"));

            Stage app = (Stage)((Node) event.getSource()).getScene().getWindow();
            app.setScene(new Scene(root));
            app.show();

        }

        else
        {
            errorLabel.setText("Wrong UserName  or  Password");
        }

    }

    @FXML
    private void forgotPassword(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("UpdatePassword.fxml"));

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }



}
