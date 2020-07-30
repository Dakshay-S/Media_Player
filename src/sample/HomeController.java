package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HomeController
{
    public void openMediaPlayer(ActionEvent event) throws Exception
    {
        Stage mediaPlayerStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MediaPlayer.fxml"));
        Parent root = loader.load();

        MediaPlayerController  mediaPlayerController = loader.getController();
        mediaPlayerController.setMediaPlayerStage(mediaPlayerStage);

        mediaPlayerController.mediaPlayerStage.setOnCloseRequest(e->{
            e.consume();
            mediaPlayerController.closeMediaPlayerRequest();
        });

        mediaPlayerStage.setScene(new Scene(root));
        mediaPlayerStage.show();


    }


    public void openTextEditor(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("textEditor.fxml"));
       Parent root = loader.load();
       TextEditorController editorController = loader.getController();
       Stage textEditorStage = new Stage();
       textEditorStage.setScene(new Scene(root));
       editorController.setTextEditorStage(textEditorStage);
       textEditorStage.setOnCloseRequest(e ->
       {
           e.consume();
           editorController.closeEditor();
       });

       textEditorStage.show();






    }


}
