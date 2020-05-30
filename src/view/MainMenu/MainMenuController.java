package view.MainMenu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import view.FxmlController;


public class MainMenuController extends FxmlController {


    public void handleRegisterButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/loginRegister/register.fxml", "Register");
    }

    public void handleLoginButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/loginRegister/login.fxml", "Login");
    }

    public void handleRemoveButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/loginRegister/removeUser.fxml", "Remove User");
    }

    public void handleChangePasswordButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/loginRegister/changePasswordPage.fxml", "Change password");
    }

    public void handleExitButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        Platform.exit();
    }
}
