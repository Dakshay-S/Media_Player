package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SaveOrNotAlert
{
    public Alert alert;

    public Alert getAlert()
    {
        return alert;
    }

    public SaveOrNotAlert()
    {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Save ?");
        alert.setContentText("Do you want to save before Exit ?");
        alert.getButtonTypes().clear();

        ButtonType save = new ButtonType("Save");
        ButtonType  dontSave = new ButtonType("Don't Save");

        alert.getButtonTypes().addAll(save , dontSave , ButtonType.CANCEL);
    }


}
