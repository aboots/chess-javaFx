package model.pieces;

import javafx.scene.image.Image;
import model.Player;

import javafx.scene.image.ImageView;


public class Queen extends Piece {
    private String team;

    public Queen(Player player, String name, int x, int y, boolean isAlive, String team) {
        super(player, name, x, y, isAlive);
        this.team = team;
        setImageViewForEachPiece();
        this.getImageView().setOnMouseClicked(e -> selectPiece());
    }

    protected void setImageViewForEachPiece() {
        if (team.equals("black")) {
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/black_queen.png")));
        } else {
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/white_queen.png")));
        }
    }

    @Override
    public boolean canPieceMove(Player player, Player enemy, int toX, int toY) {
        Rook rook = new Rook(player, super.getName(), super.getX(), super.getY(), true, this.team);
        Bishop bishop = new Bishop(player, super.getName(), super.getX(), super.getY(), true, this.team);
        if (rook.canPieceMove(player, enemy, toX, toY) || bishop.canPieceMove(player, enemy, toX, toY))
            return true;
        return false;
    }
}

