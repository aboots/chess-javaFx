package model;

import model.pieces.Piece;

public class Move {
    private Piece piece;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private Piece killedPiece;

    public Move(Piece piece, int fromX, int fromY, int toX, int toY, Piece killedPiece) {
        this.piece = piece;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.killedPiece = killedPiece;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public Piece getKilledPiece() {
        return killedPiece;
    }

    @Override
    public String toString() {
        if (killedPiece == (null))
            return piece + " " + fromX + "," + fromY + " to " + toX + "," + toY;
        else
            return piece + " " + fromX + "," + fromY + " to " + toX + "," + toY + " destroyed " + killedPiece;
    }
}
