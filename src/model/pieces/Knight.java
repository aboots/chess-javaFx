package model.pieces;

import javafx.scene.image.Image;
import model.Player;

import javafx.scene.image.ImageView;


public class Knight extends Piece {
    private String team;

    public Knight(Player player, String name, int x, int y, boolean isAlive, String team) {
        super(player, name, x, y, isAlive);
        this.team = team;
        setImageViewForEachPiece();
        this.getImageView().setOnMouseClicked(e -> selectPiece());
    }

    protected void setImageViewForEachPiece() {
        if (team.equals("black")){
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/black_knight.png")));
        }else{
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/white_knight.png")));
        }
    }

    @Override
    public boolean canPieceMove(Player player, Player enemy, int toX, int toY) {
        if ((Math.abs(toX - super.getX()) == 2 && Math.abs(toY - super.getY()) == 1) ||
                (Math.abs(toX - super.getX()) == 1 && Math.abs(toY - super.getY()) == 2)) {
            return true;
        }
        return false;
    }
}

