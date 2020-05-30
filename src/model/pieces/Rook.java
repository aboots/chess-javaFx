package model.pieces;

import javafx.scene.image.Image;
import model.Player;

import javafx.scene.image.ImageView;


public class Rook extends Piece {
    private String team;

    public Rook(Player player, String name, int x, int y, boolean isAlive, String team) {
        super(player, name, x, y, isAlive);
        this.team = team;
        setImageViewForEachPiece();
        this.getImageView().setOnMouseClicked(e -> selectPiece());
    }

    protected void setImageViewForEachPiece() {
        if (team.equals("black")){
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/black_rook.png")));
        }else{
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/white_rook.png")));
        }
    }

    @Override
    public boolean canPieceMove(Player player, Player enemy, int toX, int toY) {
        if (toX == super.getX() || toY == super.getY()) {
            if (toX == super.getX() && toY > super.getY()) {
                for (int i = 1; i < toY - super.getY(); i++)
                    if (player.doesPlayerHavePieceHere(super.getX(), super.getY() + i) ||
                            enemy.doesPlayerHavePieceHere(super.getX(), super.getY() + i)) {
                        return false;
                    }
                return true;
            } else if (toX == super.getX() && toY < super.getY()) {
                for (int i = 1; i < super.getY() - toY; i++)
                    if (player.doesPlayerHavePieceHere(super.getX(), super.getY() - i) ||
                            enemy.doesPlayerHavePieceHere(super.getX(), super.getY() - i)) {
                        return false;
                    }
                return true;
            } else if (toY == super.getY() && toX < super.getX()) {
                for (int i = 1; i < super.getX() - toX; i++)
                    if (player.doesPlayerHavePieceHere(super.getX() - i, super.getY()) ||
                            enemy.doesPlayerHavePieceHere(super.getX() - i, super.getY())) {
                        return false;
                    }
                return true;
            } else if (toY == super.getY() && toX > super.getX()) {
                for (int i = 1; i < toX - super.getX(); i++)
                    if (player.doesPlayerHavePieceHere(super.getX() + i, super.getY()) ||
                            enemy.doesPlayerHavePieceHere(super.getX() + i, super.getY())) {
                        return false;
                    }
                return true;
            } else
                return false;
        }
        return false;
    }
}
