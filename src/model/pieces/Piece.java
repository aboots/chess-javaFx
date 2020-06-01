package model.pieces;


import javafx.scene.control.Alert;
import logicControllers.GameLogicController;
import model.Player;

import javafx.scene.image.ImageView;


public abstract class Piece {
    private GameLogicController gameLogicController;
    private Player player;
    private String name;
    private int x;
    private int y;
    private boolean isAlive;
    private ImageView imageView;

    public Piece(Player player, String name, int x, int y, boolean isAlive) {
        this.gameLogicController = player.getGameLogicController();
        this.player = player;
        this.name = name;
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public abstract boolean canPieceMove(Player player, Player enemy, int toX, int toY);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void selectPiece() {
        if (!this.player.isThisPlayerTurn())
            return;
        Player enemy = null;
        if (player.equals(gameLogicController.getPlayerWhite()))
            enemy = gameLogicController.getPlayerBlack();
        else
            enemy = gameLogicController.getPlayerWhite();
        try {
            gameLogicController.selectPiece(player, x, y, enemy);
            System.out.println("piece selected");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("selecting");
            alert.setHeaderText("error during selecting");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    @Override
    public String toString() {
        return this.name;
    }
}