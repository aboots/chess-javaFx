package model.pieces;

import javafx.scene.image.Image;
import model.Player;

import javafx.scene.image.ImageView;


public class Bishop extends Piece {
    private String team;

    public Bishop(Player player, String name, int x, int y, boolean isAlive, String team) {
        super(player, name, x, y, isAlive);
        this.team = team;
        setImageViewForEachPiece();
        this.getImageView().setOnMouseClicked(e -> selectPiece());
    }

    protected void setImageViewForEachPiece() {
        if (team.equals("black")){
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/black_bishop.png")));
        }else{
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/white_bishop.png")));
        }
    }
    @Override
    public boolean canPieceMove(Player player, Player enemy, int toX, int toY) {

        if (toY - super.getY() == toX - super.getX() || toX - super.getX() == super.getY() - toY) {
            if (toY > super.getY() && toX > super.getX()) {
                for (int i = 1; i <= toX - super.getX() - 1; i++) {
                    if (enemy.doesPlayerHavePieceHere(super.getX() + i, super.getY() + i) ||
                            player.doesPlayerHavePieceHere(super.getX() + i, super.getY() + i)) {
                        return false;
                    }
                }
                return true;
            } else if (toY < super.getY() && toX > super.getX()) {
                for (int i = 1; i <= toX - super.getX() - 1; i++) {
                    if (enemy.doesPlayerHavePieceHere(super.getX() + i, super.getY() - i) ||
                            player.doesPlayerHavePieceHere(super.getX() + i, super.getY() - i)) {
                        return false;
                    }
                }
                return true;
            } else if (toY > super.getY() && toX < super.getX()) {
                for (int i = 1; i <= super.getX() - toX - 1; i++) {
                    if (enemy.doesPlayerHavePieceHere(super.getX() - i, super.getY() + i) ||
                            player.doesPlayerHavePieceHere(super.getX() - i, super.getY() + i)) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int i = 1; i <= super.getX() - toX - 1; i++) {
                    if (enemy.doesPlayerHavePieceHere(super.getX() - i, super.getY() - i) ||
                            player.doesPlayerHavePieceHere(super.getX() - i, super.getY() - i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
