package logicControllers;

import javafx.scene.control.ChoiceDialog;
import model.Move;
import model.Player;
import model.User;
import model.pieces.*;
import view.Game.Game;

import java.util.ArrayList;
import java.util.Arrays;

public class GameLogicController {
    private ArrayList<Move> moves;
    private long totalNumberOfMoves;
    private long limit;
    private Player playerBlack;
    private Player playerWhite;
    private Game game;

    public GameLogicController(long limit, User user1, User user2, int undoNumber, int limitTime) {
        this.moves = new ArrayList<>();
        this.totalNumberOfMoves = 0;
        if (limit == 0)
            this.totalNumberOfMoves = (-40000000);
        this.playerWhite = new Player(user1, undoNumber, true, "white", this);
        this.playerBlack = new Player(user2, undoNumber, false, "black", this);
        this.limit = limit;
        this.game = new Game(this, limitTime);
    }

    public long getTotalNumberOfMoves() {
        return totalNumberOfMoves;
    }

    public long getLimit() {
        return limit;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public void selectPiece(Player player, int x, int y, Player enemy) throws Exception {
        if (!(isNumberValid(x) && isNumberValid(y))) {
            throw new Exception("wrong coordination");
        }
        if (enemy.doesPlayerHavePieceHere(x, y)) {
            throw new Exception("you can only select one of your pieces");
        }
        if (!(player.doesPlayerHavePieceHere(x, y))) {
            throw new Exception("no piece on this spot");
        }
        player.setSelectedPiece(player.getPieceByCoordinate(x, y));
        game.showAvailablepathes(showAvailableTiles());
    }

    private int[][] showAvailableTiles() {
        int[][] tiles = new int[9][9];
        Player playerWhichIsTurn = null;
        Player enemy = null;
        if (playerBlack.isThisPlayerTurn()) {
            playerWhichIsTurn = playerBlack;
            enemy = playerWhite;
        } else {
            playerWhichIsTurn = playerWhite;
            enemy = playerBlack;
        }
        for (int i = 8; i >= 1; i--) {
            for (int j = 8; j >= 1; j--) {
                if (playerWhichIsTurn.getSelectedPiece().canPieceMove(playerWhichIsTurn, enemy, i, j) && playerWhichIsTurn.getPieceByCoordinate(i, j) == null) {
                    tiles[i][j] = 1;
                    System.out.println("i j = " + i + " " + j);
                }
                if (playerWhichIsTurn.getSelectedPiece().getY() == j && playerWhichIsTurn.getSelectedPiece().getX() == i)
                    tiles[i][j] = (-1);
            }
        }
        return tiles;
    }

    public boolean isNumberValid(int number) {
        if (number >= 1 && number <= 8)
            return true;
        return false;
    }

    public boolean movePiece(Player player, int toX, int toY, Player enemy) throws Exception {
        if (player.isPlayerMoveInTurn()) {
            System.out.println("already moved");
            throw new Exception("already moved");
        }
        if (!(isNumberValid(toX) && isNumberValid(toY))) {
            System.out.println("wrong coordination");
            throw new Exception("already moved");
        }
        if (player.getSelectedPiece() == null) {
            System.out.println("do not have any selected piece");
            throw new Exception("do not have any selected piece");
        }
        if ((!(player.getSelectedPiece().canPieceMove(player, enemy, toX, toY))) || (player.doesPlayerHavePieceHere(toX, toY))) {
            System.out.println("cannot move to the spot");
            throw new Exception("cannot move to the spot");
        }
        if (!(enemy.doesPlayerHavePieceHere(toX, toY))) {
            this.totalNumberOfMoves += 1;
            this.moves.add(new Move(player.getSelectedPiece(), player.getSelectedPiece().getX(), player.getSelectedPiece().getY(), toX, toY, null));
            player.getSelectedPiece().setX(toX);
            player.getSelectedPiece().setY(toY);
            System.out.println("moved");
        } else {
            this.totalNumberOfMoves += 1;
            this.moves.add(new Move(player.getSelectedPiece(), player.getSelectedPiece().getX(), player.getSelectedPiece().getY(), toX, toY, enemy.getPieceByCoordinate(toX, toY)));
            player.getSelectedPiece().setX(toX);
            player.getSelectedPiece().setY(toY);
            enemy.deleteDeadPiece(enemy.getPieceByCoordinate(toX, toY));
            System.out.println("rival piece destroyed");
        }
        player.setPlayerMoveInTurn(true);
        if (player.getSelectedPiece() instanceof Pawn) {
            Pawn pawn = (Pawn) player.getSelectedPiece();
            pawn.moved();
        }
        for (int[] row : game.getAvailablepathes())
            Arrays.fill(row, 0);
        return true;
    }

    public boolean nextTurn() throws Exception {
        if (playerBlack.isThisPlayerTurn()) {
            if (!(playerBlack.isPlayerMoveInTurn())) {
                throw new Exception("you must move then proceed to next turn");
            }
            if (isPlayerWin(playerBlack, playerWhite)) {
                System.out.println("turn completed");
                System.out.println("player " + playerBlack.getUser().getUserName() + " with color black won");
                this.endOfMatchOneWin(playerBlack, playerWhite);
                throw new Exception("player " + playerBlack.getUser().getUserName() + " with color black won");
            }
            if (playerBlack.getSelectedPiece() instanceof Pawn)
                checkPawn((Pawn) playerBlack.getSelectedPiece(), "black");
            playerBlack.setUndoThisTurn(false);
            playerBlack.setSelectedPiece(null);
            playerBlack.setPlayerMoveInTurn(false);
            playerBlack.setThisPlayerTurn(false);
            playerWhite.setPlayerMoveInTurn(false);
            playerWhite.setSelectedPiece(null);
            playerWhite.setUndoThisTurn(false);
            playerWhite.setThisPlayerTurn(true);
        } else {
            if (!(playerWhite.isPlayerMoveInTurn())) {
                throw new Exception("you must move then proceed to next turn");
            }
            if (isPlayerWin(playerWhite, playerBlack)) {
                System.out.println("turn completed");
                System.out.println("player " + playerWhite.getUser().getUserName() + " with color white won");
                this.endOfMatchOneWin(playerWhite, playerBlack);
                throw new Exception("player " + playerWhite.getUser().getUserName() + " with color white won");
            }
            if (playerWhite.getSelectedPiece() instanceof Pawn)
                checkPawn((Pawn) playerWhite.getSelectedPiece(), "white");
            playerBlack.setThisPlayerTurn(true);
            playerWhite.setThisPlayerTurn(false);
            playerBlack.setUndoThisTurn(false);
            playerBlack.setSelectedPiece(null);
            playerBlack.setPlayerMoveInTurn(false);
            playerWhite.setPlayerMoveInTurn(false);
            playerWhite.setSelectedPiece(null);
            playerWhite.setUndoThisTurn(false);
        }
        System.out.println("turn completed");
        if (this.totalNumberOfMoves == limit) {
            System.out.println("draw");
            playerBlack.getUser().setScore(playerBlack.getUser().getScore() + 1);
            playerWhite.getUser().setScore(playerWhite.getUser().getScore() + 1);
            playerBlack.getUser().setDraws(playerBlack.getUser().getDraws() + 1);
            playerWhite.getUser().setDraws(playerWhite.getUser().getDraws() + 1);
            throw new Exception("End of match. draw happened!");
        }

        return false;
    }

    private void checkPawn(Pawn piece, String team) {
        if (team.equals("black")) {
            if (piece.getX() != 1)
                return;
        } else if (team.equals("white")) {
            if (piece.getX() != 8)
                return;
        }
        String choices[] = {"Queen", "Bishop", "Rook", "Knight"};
        ChoiceDialog dialog = new ChoiceDialog(choices[0], choices);
        dialog.setHeaderText("choice to convert your pawn!");
        dialog.setContentText("please choice a piece that you want your pawn to convert to it");
        dialog.showAndWait();
        if (dialog.getSelectedItem().equals("Queen")) {
            addPiece(piece, team, "Queen");
        } else if (dialog.getSelectedItem().equals("Bishop")) {
            addPiece(piece, team, "Bishop");
        } else if (dialog.getSelectedItem().equals("Rook")) {
            addPiece(piece, team, "Rook");
        } else if (dialog.getSelectedItem().equals("Knight")) {
            addPiece(piece, team, "Knight");
        }
    }

    private void addPiece(Pawn piece, String team, String newPieceType) {
        if (newPieceType.equals("Bishop")) {
            if (team.equals("black"))
                piece.getPlayer().addPiece(new Bishop(piece.getPlayer(), "Bb", 1, piece.getY(), true, "black"));
            else
                piece.getPlayer().addPiece(new Bishop(piece.getPlayer(), "Bw", 8, piece.getY(), true, "white"));
        } else if (newPieceType.equals("Knight")) {
            if (team.equals("black"))
                piece.getPlayer().addPiece(new Knight(piece.getPlayer(), "Nb", 1, piece.getY(), true, "black"));
            else
                piece.getPlayer().addPiece(new Knight(piece.getPlayer(), "Nw", 8, piece.getY(), true, "white"));
        } else if (newPieceType.equals("Queen")) {
            if (team.equals("black"))
                piece.getPlayer().addPiece(new Queen(piece.getPlayer(), "Qb", 1, piece.getY(), true, "black"));
            else
                piece.getPlayer().addPiece(new Queen(piece.getPlayer(), "Qw", 8, piece.getY(), true, "white"));
        } else if (newPieceType.equals("Rook")) {
            if (team.equals("black"))
                piece.getPlayer().addPiece(new Rook(piece.getPlayer(), "Rb", 1, piece.getY(), true, "black"));
            else
                piece.getPlayer().addPiece(new Rook(piece.getPlayer(), "Rw", 8, piece.getY(), true, "white"));
        }
        piece.getPlayer().deleteDeadPiece(piece);
    }

    private boolean isPlayerWin(Player player, Player enemy) {
        if (enemy.getKing() == null)
            return true;
        return false;
    }

    private void endOfMatchOneWin(Player player, Player enemy) {
        player.getUser().setScore(player.getUser().getScore() + 3);
        player.getUser().setWins(player.getUser().getWins() + 1);
        enemy.getUser().setLosses(enemy.getUser().getLosses() + 1);
    }

    public void undoProcess() throws Exception {
        Player player;
        Player enemy;
        if (playerWhite.isThisPlayerTurn()) {
            player = playerWhite;
            enemy = playerBlack;
        } else {
            player = playerBlack;
            enemy = playerWhite;
        }
        if (player.getUndoRemaining() == 0) {
            throw new Exception("you cannot undo anymore");
        }
        if (!(player.isPlayerMoveInTurn())) {
            throw new Exception("you must move before undo");
        }
        if (player.isUndoThisTurn()) {
            throw new Exception("you have used your undo for this turn");
        }
        player.setUndoThisTurn(true);
        this.totalNumberOfMoves -= 1;
        player.setUndoRemaining(player.getUndoRemaining() - 1);
        Move move = moves.get(moves.size() - 1);
        moves.remove(move);
        player.setPlayerMoveInTurn(false);
        //player.setSelectedPiece(move.getPiece());
        move.getPiece().setX(move.getFromX());
        move.getPiece().setY(move.getFromY());
        if (move.getKilledPiece() != null) {
            move.getKilledPiece().setAlive(true);
            enemy.addPiece(move.getKilledPiece());
        }
        if (move.getPiece() instanceof Pawn) {
            Pawn pawn = (Pawn) move.getPiece();
            if (pawn.getNumberOfMoves() <= 1) {
                pawn.setDidFirstMove(false);
                pawn.setNumberOfMoves(0);
            } else {
                pawn.setNumberOfMoves(pawn.getNumberOfMoves() - 1);
            }
        }
        game.UndoMoveAnimation(move.getFromX(),move.getFromY());
        System.out.println("undo completed");
    }

    public String forfeitprocess() {
        Player player;
        Player enemy;
        if (playerWhite.isThisPlayerTurn()) {
            player = playerWhite;
            enemy = playerBlack;
        } else {
            player = playerBlack;
            enemy = playerWhite;
        }
        player.getUser().setScore(player.getUser().getScore() - 1);
        enemy.getUser().setScore(enemy.getUser().getScore() + 2);
        player.getUser().setLosses(player.getUser().getLosses() + 1);
        enemy.getUser().setWins(enemy.getUser().getWins() + 1);
        System.out.println("you have forfeited");
        return "player " + enemy.getUser().getUserName() + " with color " + enemy.getTeam() + " won";
    }
}
