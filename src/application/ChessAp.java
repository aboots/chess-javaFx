package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class ChessAp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu/mainMenu.fxml"));
        this.primaryStage.setTitle("Chess");
        this.primaryStage.getIcons().add(new Image("/Resources/icon.png"));
        this.primaryStage.setScene(new Scene(root, 1000, 1000));
        this.primaryStage.show();
    }

    public static void playMusic(boolean show) {
        URL resource2 = ChessAp.class.getResource("/Resources/musics/chess.wav");
        AudioClip audioClip = new AudioClip(resource2.toExternalForm());
        audioClip.setVolume(0.04);
        audioClip.setCycleCount(INDEFINITE);
        if (show) {
            audioClip.play();
            System.out.println("music played");
        }else{
            audioClip.stop();
            System.out.println("music stoped");
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
