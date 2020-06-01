package view;

import application.ChessAp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public abstract class FxmlController {

    public void setScene(String addres, String title) {
        Stage stage = ChessAp.getPrimaryStage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(addres));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 1000));
        stage.show();
    }

    public Optional<ButtonType> showAlert(Alert.AlertType alertType, String title, String header, String contentText) {
        Alert alert = new Alert(alertType);
        if (alertType == Alert.AlertType.ERROR)
            playErrorMusic();
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

    public void playButtonMusic() {
        URL resource2 = ChessAp.class.getResource("/Resources/musics/buttonsEffects/Keyboard_Button_1-fesliyanstudios.com.mp3");
        realPlay(resource2);
    }

    public void playErrorMusic() {
        URL resource2 = ChessAp.class.getResource("/Resources/musics/buttonsEffects/Error-sound.mp3");
        realPlay(resource2);
    }

    private void realPlay(URL url) {
        AudioClip audioClip = new AudioClip(url.toExternalForm());
        audioClip.setVolume(0.2);
        audioClip.play();
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("effect played");
    }

    public void playChessPieceMusics() {
        URL resource2 = ChessAp.class.getResource("/Resources/musics/chessMove.wav");
        realPlay(resource2);
    }

}
