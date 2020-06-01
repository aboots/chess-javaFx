package model.pieces;

import model.Player;

import javafx.scene.image.ImageView;

import java.awt.*;

import javafx.scene.image.Image;


public class Pawn extends Piece {
    private String team;
    private boolean didFirstMove;
    private int numberOfMoves;

    public Pawn(Player player, String name, int x, int y, boolean isAlive, String team) {
        super(player, name, x, y, isAlive);
        this.team = team;
        this.numberOfMoves = 0;
        setImageViewForEachPiece();
        this.getImageView().setOnMouseClicked(e -> selectPiece());
    }

    public void setDidFirstMove(boolean didFirstMove) {
        this.didFirstMove = didFirstMove;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public boolean isDidFirstMove() {
        return didFirstMove;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void moved() {
        if (!this.isDidFirstMove())
            setDidFirstMove(true);
        this.numberOfMoves++;
    }

    protected void setImageViewForEachPiece() {
        if (team.equals("black")) {
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/black_pawn.png")));
        } else {
            this.setImageView(new ImageView(new Image("/Resources/piecesphotos/white_pawn.png")));
        }
    }

    @Override
    public boolean canPieceMove(Player player, Player enemy, int toX, int toY) {
        if (team.equals("white")) {
            if (!this.didFirstMove) {
                if ((toX - super.getX() == 1) && toY == super.getY() && enemy.doesPlayerHavePieceHere(toX, toY) == false
                        && player.doesPlayerHavePieceHere(toX, toY) == false) {
                    return true;
                }
                if (toX - super.getX() == 2 && super.getY() == toY && enemy.doesPlayerHavePieceHere(toX - 1, toY) == false &&
                        enemy.doesPlayerHavePieceHere(toX, toY) == false && player.doesPlayerHavePieceHere(toX - 1, toY) == false &&
                        player.doesPlayerHavePieceHere(toX, toY) == false) {
                    return true;
                }
                if (toX - super.getX() == 1 && (toY - super.getY() == 1 || toY - super.getY() == (-1)) && enemy.doesPlayerHavePieceHere(toX, toY) == true) {
                    return true;
                }
                return false;
            } else {
                if (toX - super.getX() == 1 && toY == super.getY() && enemy.doesPlayerHavePieceHere(toX, toY) == false) {
                    return true;
                }
                if (toX - super.getX() == 1 && (toY - super.getY() == 1 || toY - super.getY() == (-1)) && enemy.doesPlayerHavePieceHere(toX, toY) == true) {
                    return true;
                }
                return false;
            }
        } else {
            if (!(this.didFirstMove)) {
                if (toX - super.getX() == -1 && toY == super.getY() && enemy.doesPlayerHavePieceHere(toX, toY) == false
                        && player.doesPlayerHavePieceHere(toX, toY) == false) {
                    return true;
                }
                if (toX - super.getX() == -2 && super.getY() == toY && enemy.doesPlayerHavePieceHere(toX + 1, toY) == false &&
                        enemy.doesPlayerHavePieceHere(toX, toY) == false && player.doesPlayerHavePieceHere(toX + 1, toY) == false
                        && player.doesPlayerHavePieceHere(toX, toY) == false) {
                    return true;
                }
                if (toX - super.getX() == -1 && (toY - super.getY() == 1 || toY - super.getY() == (-1)) &&
                        enemy.doesPlayerHavePieceHere(toX, toY) == true) {
                    return true;
                }
                return false;
            } else {
                if (toX - super.getX() == -1 && toY == super.getY() && enemy.doesPlayerHavePieceHere(toX, toY) == false) {
                    return true;
                }
                if (toX - super.getX() == -1 && (toY - super.getY() == 1 || toY - super.getY() == (-1)) &&
                        enemy.doesPlayerHavePieceHere(toX, toY) == true) {
                    return true;
                }
                return false;
            }
        }
    }
}
