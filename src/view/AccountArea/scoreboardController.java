package view.AccountArea;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logicControllers.AccountAreaLogicController;
import model.User;
import view.FxmlController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class scoreboardController extends FxmlController implements Initializable {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, String> score;
    @FXML
    private TableColumn<User, String> wins;
    @FXML
    private TableColumn<User, String> looses;
    @FXML
    private TableColumn<User, String> draws;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        wins.setCellValueFactory(new PropertyValueFactory<>("wins"));
        draws.setCellValueFactory(new PropertyValueFactory<>("draws"));
        looses.setCellValueFactory(new PropertyValueFactory<>("losses"));
        ArrayList<User> allusers = AccountAreaLogicController.scoreBoard();
        ObservableList<User> observableListOfUsers = FXCollections.observableList(allusers);
        tableView.getItems().setAll(observableListOfUsers);
    }

    public void handleBackButtonAction(ActionEvent actionEvent) {
        playButtonMusic();
        setScene("/view/AccountArea/accountArea.fxml", "Account Area");
    }
}
