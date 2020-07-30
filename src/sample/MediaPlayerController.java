package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Math.floor;
import static java.lang.String.format;

public class MediaPlayerController implements Initializable
{

    Stage mediaPlayerStage;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button playPauseBtn;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider timeSlider;

    @FXML
    private Label timelabel;

    @FXML
    private MenuBar menuBar;



    private Media media;
    private MediaPlayer mediaPlayer;


    public void setMediaPlayerStage(Stage mediaPlayerStage) {
        this.mediaPlayerStage = mediaPlayerStage;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        mediaView.fitHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.fitWidthProperty().bind(Bindings.selectDouble(mediaView.sceneProperty() , "width"));

       /* mediaView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               Platform.runLater(new Runnable() {
                   @Override
                   public void run() {
                       controlPane.setVisible(true);
                       try {
                           Thread.sleep(3000);
                       }
                       catch(Exception e)
                       {
                           System.out.println(e);
                       }

                       controlPane.setVisible(true);

                   }
               });
            }
        });*/

        media = new Media(new File("src\\sample\\Video.mp4").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
        Menu menu = new Menu("File");
        MenuItem menuItem = new MenuItem("Open");
        menu.getItems().add(menuItem);
        menuBar.getMenus().add(menu);
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newVideo();
            }
        });


            //mediaView.s

        /*timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(timeSlider.getValue()));
            }
        });*/

        timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(timeSlider.getValue()));
            }
        });


        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue());
            }
        });

        // This method of updating time slider dynamically using listener terribly failed
              timeSlider.valueProperty().addListener(new InvalidationListener() {
                  @Override
                  public void invalidated(Observable observable) {
                      if(timeSlider.isValueChanging())
                      mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(timeSlider.getValue()));
                  }
              });


               mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                   @Override
                   public void invalidated(Observable observable) {
                       timeSlider.setValue( (mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis()));
                       timelabel.setText(formatTime(mediaPlayer.getCurrentTime() ,  mediaPlayer.getTotalDuration()));
                   }
               });


    }


    public void closeMediaPlayerRequest()
    {
       playPauseAction();


        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Close Media Player ?");
        alert.setContentText("Hit Close to  close the media player");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.CANCEL , ButtonType.CLOSE);

        Optional<ButtonType> option = alert.showAndWait();

        if(option.get() == ButtonType.CLOSE)
        {
            mediaPlayer.stop();
            mediaPlayerStage.close();
        }
        else if(option.get() == ButtonType.CANCEL)
        {
            playPauseAction();
        }



    }

    public void printDuration()
    {

        System.out.println(mediaPlayer.getCurrentTime().toSeconds());
    }


    private void playAction()
    {
        mediaPlayer.play();
        Image image = new Image(getClass().getResourceAsStream("/Pause.png"));

        playPauseBtn.setGraphic(new ImageView(image));

    }


    private  void pauseAction()
    {
        mediaPlayer.pause();
        Image image = new Image(getClass().getResourceAsStream("/Play.png"));
        playPauseBtn.setGraphic(new ImageView(image));
    }


    private void startMediaFrom(Duration time)
    {
        mediaPlayer.seek(time);
        //playAction();
    }

    @FXML
    private void playPauseAction()
    {

        MediaPlayer.Status status = mediaPlayer.getStatus();

        if(status == MediaPlayer.Status.PAUSED
            || status == MediaPlayer.Status.HALTED
            || status == MediaPlayer.Status.STOPPED)
        {
            playAction();

        }


        else
            pauseAction();

    }


    public void replayAction()
    {
        mediaPlayer.seek(mediaPlayer.getStartTime());
        playAction();
        timeSlider.setValue(0);

    }

    public void skipForward()
    {
        Duration time =  mediaPlayer.getCurrentTime();
        time = time.add(Duration.seconds(30));
        if(time.lessThan(mediaPlayer.getTotalDuration()))
            startMediaFrom(time);

        else
        {
            mediaPlayer.seek(mediaPlayer.getTotalDuration());
            pauseAction();

        }

    }


    public void skipBackward()
    {
        Duration time = mediaPlayer.getCurrentTime();
        time = time.subtract(Duration.seconds(30));

        if(time.lessThan(mediaPlayer.getStartTime()))
            replayAction();


        else
            startMediaFrom(time);



    }


    public void newVideo()
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("videos" , "*.mp4"), new FileChooser.ExtensionFilter("mp3" , "*.mp3"));
        //fc.setInitialDirectory(new File("D:\\JAVA"));

        File file = fc.showOpenDialog(null);
        media = new Media(file.toURI().toString());

        mediaPlayer.stop();

        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                timeSlider.setValue( (mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis()));
                timelabel.setText(formatTime(mediaPlayer.getCurrentTime() ,  mediaPlayer.getTotalDuration()));
            }
        });
        mediaPlayer.setVolume(volumeSlider.getValue());
        mediaView.setMediaPlayer(mediaPlayer);

        replayAction();

    }



    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            }
            else {
                return format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        }
        else {
            if (elapsedHours > 0) {
                return format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
}

