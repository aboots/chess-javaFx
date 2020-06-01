package logicControllers;

import model.User;

import java.util.ArrayList;

public class AccountAreaLogicController {

    public static void logout() {
        User user = User.getUserWhoIsLogin();
        user.setUserLogin(false);
    }

    public static ArrayList<User> scoreBoard() {
        return User.sortUsersBaseOnScore();
    }

    public static void startNewGame(String userName, String limit, String undoNumber, String timeLimit) throws Exception {
        if (!limit.matches("^(-?\\d+)$"))
            throw new Exception("turn  should be a number!");
        if (!undoNumber.matches("^(\\d+)$"))
            throw new Exception("undo number should be a positive number!");
        if (!timeLimit.matches("^(\\d+)$"))
            throw new Exception("time limit should be a positive number!");
        long limitOfMoves = Long.parseLong(limit);
        if (!(LogicLoginRegisterController.isStringValid(userName))) {
            throw new Exception("username format is invalid!");
        }
        if (limitOfMoves < 0) {
            throw new Exception("turn limit should be positive to have a limit or 0 for no limit");
        }
        User user = User.getUserWhoIsLogin();
        if (user.getUserName().equals(userName)) {
            throw new Exception("you must choose another player to start a game");
        }
        if (User.getUserByName(userName) == null) {
            throw new Exception("no user exists with this username");
        }
    }
}
