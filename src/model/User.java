package model;


import java.util.ArrayList;
import java.util.Collections;

public class User {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private String userName;
    private String passWord;
    private int score;
    private int wins;
    private int draws;
    private int losses;
    private boolean isUserLogin;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.isUserLogin = false;
        allUsers.add(this);
    }

    public static void removeUser(User user) {
        allUsers.remove(user);
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public static User getUserByName(String username) {
        for (User user : allUsers) {
            if (user.getUserName().equals(username))
                return user;
        }
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setUserLogin(boolean userLogin) {
        isUserLogin = userLogin;
    }

    public static User getUserWhoIsLogin() {
        for (User user : allUsers) {
            if (user.isUserLogin)
                return user;
        }
        return null;
    }

    public static ArrayList<String> sortUsersBaseOnAlphabet() {
        ArrayList<String> list = new ArrayList<>();
        for (User user : allUsers) {
            list.add(user.getUserName());
        }
        Collections.sort(list);
        return list;
    }

    public static ArrayList<User> sortUsersBaseOnScore() {
        ArrayList<User> allusersTemp = (ArrayList<User>) allUsers.clone();
        ArrayList<User> sortList = new ArrayList<>();
        while (sortList.size() != allUsers.size()) {
            User bestUser = allusersTemp.get(0);
            for (User user : allusersTemp) {
                if (user.score > bestUser.score)
                    bestUser = user;
                else if (user.score == bestUser.score) {
                    if (user.wins > bestUser.wins)
                        bestUser = user;
                    else if (user.wins == bestUser.wins) {
                        if (user.draws > bestUser.draws)
                            bestUser = user;
                        else if (user.draws == bestUser.draws) {
                            if (user.losses < bestUser.losses)
                                bestUser = user;
                            else if (user.losses == bestUser.losses) {
                                if (user.userName.compareTo(bestUser.userName) < 0)
                                    bestUser = user;
                            }
                        }
                    }
                }
            }
            allusersTemp.remove(bestUser);
            sortList.add(bestUser);
        }
        return sortList;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    @Override
    public String toString() {
        return userName + " " + score + " " + wins + " " + draws + " " + losses;
    }
}
