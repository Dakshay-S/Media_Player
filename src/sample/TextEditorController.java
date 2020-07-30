package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class TextEditorController
{
    Stage textEditorStage;

    public void setTextEditorStage(Stage textEditorStage) {
        this.textEditorStage = textEditorStage;
    }

    public void closeEditor()
    {
        SaveOrNotAlert alert = new SaveOrNotAlert();
        Optional<ButtonType> selection = alert.getAlert().showAndWait();

        if(selection.get().getText() == "Don't Save")
        {
            System.out.println("almighty");
            textEditorStage.close();
        }

    }

    @FXML
    private void saveFile()
    {}

    @FXML
    private void saveNewFile()
    {

    }


}
