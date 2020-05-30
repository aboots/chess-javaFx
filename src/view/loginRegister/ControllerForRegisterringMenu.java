package view.loginRegister;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logicControllers.LogicLoginRegisterController;
import view.FxmlController;

public class ControllerForRegisterringMenu extends FxmlController {
    @FXML
    PasswordField passwordField;
    @FXML
    TextField textField;
    @FXML
    PasswordField newpassWordField;

    public void handleBackButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/MainMenu/mainMenu.fxml", "Chess");
    }

    public void handleLoginButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        String userName = textField.getText();
        String password = passwordField.getText();
        try {
            LogicLoginRegisterController.loginUser(userName, password);
            showAlert(Alert.AlertType.INFORMATION, "Login", null, "you have Logined succesfully!");
            setScene("/view/AccountArea/accountArea.fxml", "Account Area");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "error occured during login", "error occured", e.getMessage());
        }
    }

    public void handleRegisterButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        String username = textField.getText();
        String passWord = passwordField.getText();
        try {
            LogicLoginRegisterController.registerUser(username, passWord);
            showAlert(Alert.AlertType.INFORMATION, "register", null, "you have registered succesfully!");
            handleBackButtonAction(new ActionEvent());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "error for registering", "error occured", e.getMessage());
        }
    }

    public void handleRemoveButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        String username = textField.getText();
        String passWord = passwordField.getText();
        try {
            LogicLoginRegisterController.removeUser(username, passWord);
            showAlert(Alert.AlertType.INFORMATION, "remove user", null, "User removed succesfully!");
            handleBackButtonAction(new ActionEvent());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "error for removing user", "error occured", e.getMessage());
        }
    }

    public void handleChangePasswordButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        String username = textField.getText();
        String oldPassWord = passwordField.getText();
        String newPassword = newpassWordField.getText();
        try {
            LogicLoginRegisterController.changePassword(username, oldPassWord, newPassword);
            showAlert(Alert.AlertType.INFORMATION, "Password change", null, "password changed succesfully!");
            handleBackButtonAction(new ActionEvent());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "error changing password", "error occured", e.getMessage());
        }
    }
}
