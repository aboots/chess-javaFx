package model.pieces;

import javafx.scene.image.Image;
import model.Player;

import javafx.scene.image.ImageView;


public class King extends Piece {
    String team;
    boolean isKingAlive;

    public King(
            Player player, String name, int x, int y, boolean isAlive, String team, boolean isKingAlive) {
        super(player, name, x, y, isAlive);
        this.team = team;
        this.isKingAlive = isKingAlive;
        setImageViewForEachPiece();
        this.getImageView().setOnMouseClicked(e -> selectPiece());
    }

    protected void setImageViewForEachPiece() {
        if (team.equals("black")){
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/black_king.png")));
        }else{
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/white_king.png")));
        }
    }

    @Override
    public boolean canPieceMove(Player player, Player enemy, int toX, int toY) {
        if (Math.abs(super.getX() - toX) <= 1 && Math.abs(super.getY() - toY) <= 1)
            return true;
        return false;
    }
}
