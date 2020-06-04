package view.AccountArea;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logicControllers.AccountAreaLogicController;
import logicControllers.GameLogicController;
import model.User;
import view.FxmlController;


import java.util.*;

import static application.ChessAp.playMusic;

public class AccountArea extends FxmlController {
    @FXML
    TextField opponentUsername;
    @FXML
    TextField turnLimit;
    @FXML
    TextField undoNumber;
    @FXML
    TextField timeLimit;

    public void handleNewGameButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/AccountArea/gameSetting.fxml", "Game setting");
    }

    public void handleScoreboardButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/AccountArea/scoreboard.fxml", "scoreboard");
    }

    public void handleLogoutButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "Logout", "Logout", "are you sure to logout?");
        if (result.get() == ButtonType.OK) {
            AccountAreaLogicController.logout();
            setScene("/view/MainMenu/mainMenu.fxml", "Chess");
        }
    }

    public void handleBackButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/AccountArea/accountArea.fxml", "Account Area");
    }

    public void handleStartGameButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        String username = opponentUsername.getText();
        String turnLimit1 = turnLimit.getText();
        String undoNumber1 = undoNumber.getText();
        String timelimit1 = timeLimit.getText();
        try {
            AccountAreaLogicController.startNewGame(username, turnLimit1, undoNumber1, timelimit1);
            showAlert(Alert.AlertType.INFORMATION, "Game", null, "game started successfully!");
            GameLogicController gameLogicController = new GameLogicController
                    (Long.parseLong(turnLimit1), User.getUserWhoIsLogin(), User.getUserByName(username), Integer.parseInt(undoNumber1), Integer.parseInt(timelimit1));
            playMusic(true);
            gameLogicController.getGame().run();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "error occured during starting game", "error occured", e.getMessage());
        }
    }
}
