package model;

import logicControllers.GameLogicController;
import model.pieces.*;

import java.util.ArrayList;

public class Player {
    private GameLogicController gameLogicController;
    private User user;
    private ArrayList<Piece> pieces;
    private int undoRemaining;
    private Piece selectedPiece;
    private boolean isThisPlayerTurn;
    private String team;
    private boolean playerMoveInTurn;
    private boolean undoThisTurn;

    public Player(User user, int undoRemaining, boolean isThisPlayerTurn, String team,GameLogicController gameLogicController) {
        this.user = user;
        this.gameLogicController=gameLogicController;
        this.pieces = new ArrayList<>();
        this.undoRemaining = undoRemaining;
        this.isThisPlayerTurn = isThisPlayerTurn;
        this.team = team;
        this.playerMoveInTurn = false;
        this.undoThisTurn = false;
        generatePieces();
    }

    public void generatePieces() {
        if (this.team.equals("white")) {
            this.pieces.add(new Rook(this, "Rw", 1, 1, true, "white"));
            this.pieces.add(new Knight(this, "Nw", 1, 2, true, "white"));
            this.pieces.add(new Bishop(this, "Bw", 1, 3, true, "white"));
            this.pieces.add(new Queen(this, "Qw", 1, 4, true, "white"));
            this.pieces.add(new King(this, "Kw", 1, 5, true, "white", true));
            this.pieces.add(new Bishop(this, "Bw", 1, 6, true, "white"));
            this.pieces.add(new Knight(this, "Nw", 1, 7, true, "white"));
            this.pieces.add(new Rook(this, "Rw", 1, 8, true, "white"));
            for (int i = 1; i <= 8; i++)
                this.pieces.add(new Pawn(this, "Pw", 2, i, true, "white"));
        } else {
            this.pieces.add(new Rook(this, "Rb", 8, 1, true, "black"));
            this.pieces.add(new Knight(this, "Nb", 8, 2, true, "black"));
            this.pieces.add(new Bishop(this, "Bb", 8, 3, true, "black"));
            this.pieces.add(new Queen(this, "Qb", 8, 4, true, "black"));
            this.pieces.add(new King(this, "Kb", 8, 5, true, "black", true));
            this.pieces.add(new Bishop(this, "Bb", 8, 6, true, "black"));
            this.pieces.add(new Knight(this, "Nb", 8, 7, true, "black"));
            this.pieces.add(new Rook(this, "Rb", 8, 8, true, "black"));
            for (int i = 1; i <= 8; i++)
                this.pieces.add(new Pawn(this, "Pb", 7, i, true, "black"));
        }
    }

    public boolean isThisPlayerTurn() {
        return isThisPlayerTurn;
    }

    public boolean doesPlayerHavePieceHere(int x, int y) {
        for (Piece piece : this.pieces) {
            if (piece.getX() == x && piece.getY() == y)
                return true;
        }
        return false;
    }

    public Piece getPieceByCoordinate(int x, int y) {
        for (Piece piece : this.pieces) {
            if (piece.getX() == x && piece.getY() == y && piece.isAlive())
                return piece;
        }
        return null;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isPlayerMoveInTurn() {
        return playerMoveInTurn;
    }

    public void deleteDeadPiece(Piece piece) {
        this.pieces.remove(piece);
        piece.setAlive(false);
    }

    public void setPlayerMoveInTurn(boolean playerMoveInTurn) {
        this.playerMoveInTurn = playerMoveInTurn;
    }

    public int getUndoRemaining() {
        return undoRemaining;
    }

    public void setThisPlayerTurn(boolean thisPlayerTurn) {
        isThisPlayerTurn = thisPlayerTurn;
    }

    public User getUser() {
        return user;
    }

    public void setUndoThisTurn(boolean undoThisTurn) {
        this.undoThisTurn = undoThisTurn;
    }

    public GameLogicController getGameLogicController() {
        return gameLogicController;
    }

    public boolean isUndoThisTurn() {
        return undoThisTurn;
    }

    public void setUndoRemaining(int undoRemaining) {
        this.undoRemaining = undoRemaining;
    }

    public void addPiece(Piece piece) {
        this.pieces.add(piece);
    }

    public String getTeam() {
        return team;
    }

    public Piece getKing() {
        for (Piece piece : pieces) {
            if (piece.getName().equals("Kb") || piece.getName().equals("Kw"))
                return piece;
        }
        return null;
    }
}
