package logicControllers;

import model.User;


public class LogicLoginRegisterController {

    public static void registerUser(String userName, String passWord) throws Exception {
        User user = User.getUserByName(userName);
        if (isUserNameAndPassWordValid(userName, passWord)) {
            if (user != null) {
                throw new Exception("a user exists with this username");
            }
            new User(userName, passWord);
        }
    }

    public static boolean isStringValid(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!((string.charAt(i) >= '0' && string.charAt(i) <= '9') || (string.charAt(i) >= 'a' && string.charAt(i) <= 'z')
                    || (string.charAt(i) >= 'A' && string.charAt(i) <= 'Z') || (string.charAt(i) == '_')))
                return false;
        }
        return true;
    }

    public static void loginUser(String userName, String passWord) throws Exception {
        User user = User.getUserByName(userName);
        if (isUserNameAndPassWordValid(userName, passWord)) {
            if (user == null) {
                throw new Exception("no user exists with this username");
            }
            if (isPassWordCorrect(user, passWord)) {
                user.setUserLogin(true);
            }
        }
    }

    public static void removeUser(String userName, String passWord) throws Exception {
        User user = User.getUserByName(userName);
        if (isUserNameAndPassWordValid(userName, passWord)) {
            if (user == null) {
                throw new Exception("no user exists with this username");
            }
            if (isPassWordCorrect(user, passWord)) {
                User.removeUser(user);
            }
        }
    }

    private static boolean isUserNameAndPassWordValid(String userName, String passWord) throws Exception {
        if (!isStringValid(userName))
            throw new Exception("username format is invalid");
        if (!isStringValid(passWord))
            throw new Exception("password format is invalid");
        return true;
    }

    private static boolean isPassWordCorrect(User user, String passWord) throws Exception {
        if (!(user.getPassWord().equals(passWord))) {
            throw new Exception("incorrect password!");
        }
        return true;
    }

    public static void changePassword(String userName, String oldPassword, String newPassword) throws Exception {
        User user = User.getUserByName(userName);
        if (isUserNameAndPassWordValid(userName, oldPassword)) {
            if (user == null) {
                throw new Exception("no user exists with this username");
            }
            if (!isStringValid(newPassword))
                throw new Exception("new password format is invalid");
            if (isPassWordCorrect(user, oldPassword)) {
                user.setPassWord(newPassword);
            }
        }
    }
}
