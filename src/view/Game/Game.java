package view.Game;

import application.ChessAp;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;


import javafx.util.Duration;
import logicControllers.GameLogicController;
import model.Player;
import view.FxmlController;

import java.util.Arrays;
import java.util.Optional;

import static application.ChessAp.playMusic;


public class Game extends FxmlController {
    private Timeline time;
    private int fullTime;
    private int timeRemaining;
    private GameLogicController gameLogicController;
    private int[][] availablepathes;
    private KeyFrame frame;
    private Label label;


    public Game(GameLogicController gameLogicController, int limitOfTime) {
        this.fullTime = limitOfTime;
        this.time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        this.gameLogicController = gameLogicController;
        availablepathes = new int[9][9];
        this.timeRemaining = fullTime;
        this.label = new Label("-time remaining : " + timeRemaining + " second");
        this.frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeRemaining--;
                label.setText("-time remaining : " + timeRemaining + " second");
                if (timeRemaining <= 0) {
                    time.stop();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    playErrorMusic();
                    alert.setTitle("lose");
                    alert.setHeaderText("lose beacase of time");
                    alert.setContentText("your time ends and you lose match");
                    alert.show();
                    playMusic(false);
                    setScene("/view/AccountArea/accountArea.fxml", "Chess");
                }
            }
        });
    }

    public void run() {
        GridPane root = buildGridPane();
        addingPiecesToBoard(root);
        addingButtons(root);
        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add("/view/Game/gameStyles.css");
        ChessAp.getPrimaryStage().setScene(scene);
        ChessAp.getPrimaryStage().setTitle("Chess");
        ChessAp.getPrimaryStage().show();
    }

    private void addingButtons(GridPane root) {
        Label label4 = new Label("-player black has " + gameLogicController.getPlayerBlack().getUndoRemaining() + " undo remaining " +
                "and player white has " + gameLogicController.getPlayerWhite().getUndoRemaining() + " undo remaining.");
        label4.setFont(new Font("Century Gothic", 18));
        label4.setTextFill(Color.DARKBLUE);
        GridPane.setHalignment(label4, HPos.CENTER);
        GridPane.setValignment(label4, VPos.BOTTOM);
        label4.setStyle("-fx-padding: 10px;");
        root.add(label4, 0, 16, 20, 2);
        Label label5 = new Label("-total number of moves : " + gameLogicController.getTotalNumberOfMoves()
                + "                            " + "       -limit of moves : " + gameLogicController.getLimit());
        label5.setFont(new Font("Century Gothic", 18));
        label5.setTextFill(Color.DARKBLUE);
        GridPane.setHalignment(label5, HPos.LEFT);
        GridPane.setValignment(label5, VPos.BOTTOM);
        label5.setStyle("-fx-padding: 10px;");
        root.add(label5, 0, 18, 20, 2);
        addTimer(root);
        Button undoButton = new Button("undo");
        stylingButton(undoButton);
        undoButton.setOnMouseClicked(e -> undoProcess());
        root.add(undoButton, 3, 25, 3, 2);
        Button forfeitButton = new Button("forfeit");
        stylingButton(forfeitButton);
        forfeitButton.setOnMouseClicked(e -> forfeit());
        root.add(forfeitButton, 6, 25, 3, 2);
        Button nextTurn = new Button("next turn");
        nextTurn.setOnMouseClicked(e -> goNextTurn());
        stylingButton(nextTurn);
        root.add(nextTurn, 9, 25, 3, 2);
    }

    private void addTimer(GridPane root) {
        time.getKeyFrames().add(frame);
        time.playFromStart();
        label.setFont(new Font("Century Gothic", 18));
        label.setTextFill(Color.RED);
        GridPane.setHalignment(label, HPos.LEFT);
        GridPane.setValignment(label, VPos.BOTTOM);
        label.setStyle("-fx-padding: 12px;");
        root.add(label, 0, 20, 20, 2);
    }

    private void forfeit() {
        playButtonMusic();
        Optional<ButtonType> result = showAlert
                (Alert.AlertType.CONFIRMATION, "forfeit", "forfeit", "are you sure to forfeit?");
        if (result.get() == ButtonType.OK) {
            showAlert(Alert.AlertType.INFORMATION, "end of match", "forfeit", gameLogicController.forfeitprocess());
            playMusic(false);
            setScene("/view/AccountArea/accountArea.fxml", "Chess");
        }
    }

    private void undoProcess() {
        playButtonMusic();
        try {
            gameLogicController.undoProcess();
            time.stop();
            time.getKeyFrames().remove(frame);
            this.run();
        } catch (Exception exception) {
            showAlert(Alert.AlertType.ERROR, "undo", "error for undo", exception.getMessage());
        }
    }

    private void stylingButton(Button button) {
        button.setStyle(
                "  -fx-background-color: #4CAF50;" +
                        "  -fx-background-radius: 8px;" +
                        "  -fx-color: white;" +
                        "  -fx-text-fill: white;" +
                        "  -fx-margin: 4px 2px;" +
                        "  -fx-border-radius: 8px;" +
                        "  -fx-border-color:white;" +
                        "  -fx-border-width: 2 2 2 2;" +
                        "  -fx-width: 100%;" +
                        "  -fx-font-size:15;");
        button.setOnMouseEntered(e -> button.setStyle("  -fx-background-color: white;" +
                "  -fx-text-fill: #4CAF50;" +
                "  -fx-color: #4CAF50;" +
                "  -fx-border-color:#4CAF50;" +
                "  -fx-border-width: 2 2 2 2;" +
                "  -fx-background-radius: 8px;" +
                "  -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle(
                "  -fx-background-color: #4CAF50;" +
                        "  -fx-background-radius: 8px;" +
                        "  -fx-color: white;" +
                        "  -fx-text-fill: white;" +
                        "  -fx-margin: 4px 2px;" +
                        "  -fx-border-radius: 8px;" +
                        "  -fx-border-color:white;" +
                        "  -fx-border-width: 2 2 2 2;" +
                        "  -fx-width: 100%;" +
                        "  -fx-font-size:15;"));
        button.setCursor(Cursor.HAND);
        button.setPrefHeight(45);
        button.setPrefWidth(120);
        button.setAlignment(Pos.CENTER);
    }

    private void addingPiecesToBoard(GridPane root) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ImageView imageView = null;
                if (gameLogicController.getPlayerBlack().doesPlayerHavePieceHere(i, j)) {
                    imageView = gameLogicController.getPlayerBlack().getPieceByCoordinate(i, j).getImageView();
                    root.add(imageView,
                            gameLogicController.getPlayerBlack().getPieceByCoordinate(i, j).getY() - 1 + 3,
                            8 - gameLogicController.getPlayerBlack().getPieceByCoordinate(i, j).getX() + 2);
                    gameLogicController.getPlayerBlack().getPieceByCoordinate(i, j).getImageView().fitWidthProperty()
                            .bind(((root.widthProperty()).multiply(0.6).divide(8)).multiply(0.7));
                    gameLogicController.getPlayerBlack().getPieceByCoordinate(i, j).getImageView().fitHeightProperty()
                            .bind(((root.heightProperty()).multiply(0.6).divide(8)).multiply(0.7));
                    GridPane.setHalignment(gameLogicController.getPlayerBlack().getPieceByCoordinate(i, j).getImageView(), HPos.CENTER);
                } else if (gameLogicController.getPlayerWhite().doesPlayerHavePieceHere(i, j)) {
                    imageView = gameLogicController.getPlayerWhite().getPieceByCoordinate(i, j).getImageView();
                    root.add(imageView,
                            gameLogicController.getPlayerWhite().getPieceByCoordinate(i, j).getY() - 1 + 3,
                            8 - gameLogicController.getPlayerWhite().getPieceByCoordinate(i, j).getX() + 2);
                    gameLogicController.getPlayerWhite().getPieceByCoordinate(i, j).getImageView().fitWidthProperty()
                            .bind(((root.widthProperty()).multiply(0.6).divide(8)).multiply(0.7));
                    gameLogicController.getPlayerWhite().getPieceByCoordinate(i, j).getImageView().fitHeightProperty()
                            .bind(((root.heightProperty()).multiply(0.6).divide(8)).multiply(0.7));
                    GridPane.setHalignment(gameLogicController.getPlayerWhite().getPieceByCoordinate(i, j).getImageView(), HPos.CENTER);
                }
                if (availablepathes[i][j] == (-1)) {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.BLUE);
                    borderGlow.setWidth(70);
                    borderGlow.setHeight(70);
                    imageView.setEffect(borderGlow);
                } else {
                    if (imageView != null) {
                        if (imageView.getEffect() != null) {
                            DropShadow borderGlow = (DropShadow) imageView.getEffect();
                            borderGlow.setHeight(1);
                            borderGlow.setWidth(1);
                        }
                    }
                }
            }
        }
    }

    private void goNextTurn() {
        playButtonMusic();
        try {
            gameLogicController.nextTurn();
            for (int[] row : this.availablepathes)
                Arrays.fill(row, 0);
            timeRemaining = fullTime;
            time.stop();
            time.getKeyFrames().remove(frame);
            this.run();
        } catch (Exception exception) {
            if (exception.getMessage().startsWith("End of match")) {
                showAlert(Alert.AlertType.INFORMATION, "end of match", "draw", exception.getMessage());
                playMusic(false);
                setScene("/view/AccountArea/accountArea.fxml", "Chess");
            } else if (exception.getMessage().startsWith("player ")) {
                showAlert(Alert.AlertType.INFORMATION, "end of match", "winnn!!", exception.getMessage());
                playMusic(false);
                setScene("/view/AccountArea/accountArea.fxml", "Chess");
            } else
                showAlert(Alert.AlertType.ERROR, "next turn", "error for next turn", exception.getMessage());
        }

    }

    private GridPane buildGridPane() {
        GridPane root = new GridPane();
        Label label1;
        if (gameLogicController.getPlayerWhite().isThisPlayerTurn())
            label1 = new Label("          White turn");
        else
            label1 = new Label("          Black turn");
        label1.setFont(new Font("Century Gothic", 30));
        label1.setTextFill(Color.DARKBLUE);
        GridPane.setHalignment(label1, HPos.CENTER);
        GridPane.setValignment(label1, VPos.TOP);
        label1.setStyle("-fx-padding: 30px;");
        root.add(label1, 4, 0, 5, 2);
        for (int i = 0; i < 8; i++) {
            Label label = new Label("" + (8 - i));
            label.setFont(new Font("Arial", 16));
            GridPane.setHalignment(label, HPos.LEFT);
            label.setStyle("-fx-padding: 10px;");
            root.add(label, 0, i + 2);
        }
        for (char c = 'A'; c <= 'H'; ++c) {
            Label label = new Label("" + c);
            label.setFont(new Font("Arial", 14));
            GridPane.setHalignment(label, HPos.CENTER);
            label.setStyle("-fx-padding: 10px;");
            root.add(label, c - 'A' + 3, 12);
        }
        root.setId("gridpane");
        root.setStyle(" -fx-background-color: #ffffcc;");
        final int size = 8;
        for (int row = 2; row < size + 2; row++) {
            for (int col = 3; col < size + 3; col++) {
                Rectangle square = new Rectangle();
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        int row2 = GridPane.getRowIndex(square);
                        //System.out.println("row "+row2);
                        int col2 = GridPane.getColumnIndex(square);
                        //System.out.println("column "+ col2);
                        if (availablepathes[10 - row2][col2 - 2] == 1) {
                            Player playerWhichIsTurned;
                            Player enemy;
                            if (gameLogicController.getPlayerWhite().isThisPlayerTurn()) {
                                playerWhichIsTurned = gameLogicController.getPlayerWhite();
                                enemy = gameLogicController.getPlayerBlack();
                            } else {
                                playerWhichIsTurned = gameLogicController.getPlayerBlack();
                                enemy = gameLogicController.getPlayerWhite();
                            }
                            try {
                                if (gameLogicController.movePiece(playerWhichIsTurned, 10 - row2, col2 - 2, enemy)) {
                                    System.out.println("can move");
                                }
                            } catch (Exception exception) {
                                showAlert(Alert.AlertType.ERROR, "error for moving", "error occured", exception.getMessage());
                            }
                            movePieceShow();
                        }
                    }
                };
                square.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                Color color;
                if ((row + col) % 2 == 0) color = Color.BEIGE;
                else color = Color.ROSYBROWN;
                if (availablepathes[10 - row][col - 2] == 1) {
                    square.setFill(Color.BROWN);
                } else {
                    square.setFill(color);
                    square.setStroke(color);
                }
                root.add(square, col, row);
                square.widthProperty().bind((root.widthProperty()).multiply(0.6).divide(size));
                square.heightProperty().bind(root.heightProperty().multiply(0.6).divide(size));
            }
        }
        root.setAlignment(Pos.TOP_CENTER);
        return root;
    }

    private void movePieceShow() {
        playChessPieceMusics();
        time.stop();
        time.getKeyFrames().remove(frame);
        this.run();
    }

    public void showAvailablepathes(int[][] availableTiles) {
        availablepathes = availableTiles;
        time.stop();
        time.getKeyFrames().remove(frame);
        this.run();
    }

    public int[][] getAvailablepathes() {
        return availablepathes;
    }
}
