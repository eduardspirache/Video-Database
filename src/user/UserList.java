package user;

import video.Movie;
import video.Serial;

import java.util.ArrayList;
import java.util.List;

import static common.Constants.ASCENDING;

public class UserList {
    private final List<User> userList;

    public UserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    //////////////////////////////// Commands ////////////////////////////////
    public String simpleCommands(String command, String userName,
                                 String show) {
        for (var user : userList)
            if (user.getUsername().equals(userName))
                if (command.equals("favorite"))
                    return user.favorite(show);
                else
                    return user.view(show);
        return null;
    }

    public String rateMovie(String userName, Movie movie, double rating) {
        for (var user : userList)
            if (user.getUsername().equals(userName))
                return user.rateMovie(movie, rating);
        return null;
    }

    public String rateSerial(String userName, Serial serial,
                             int season, double rating) {
        for (var user : userList)
            if (user.getUsername().equals(userName))
                return user.rateSerial(serial, season, rating);
        return null;
    }

    //////////////////////////////// Queries ////////////////////////////////
    public List<User> sortByRating(int n, String sortType) {
        List<User> sorted = new ArrayList<>(userList);
        sorted.removeIf(a -> a.getCountRatings() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if(a.getCountRatings() != b.getCountRatings())
                    return Integer.compare(a.getCountRatings(),
                            b.getCountRatings());
                return a.getUsername().compareTo(b.getUsername());
            });
        } else {
            sorted.sort((a, b) -> {
                if(a.getCountRatings() != b.getCountRatings())
                    return Integer.compare(b.getCountRatings(),
                            a.getCountRatings());
                return b.getUsername().compareTo(a.getUsername());
            });
        }
        if (n < sorted.size())
            return sorted.subList(0, n - 1);
        else
            return sorted;
    }

    ///////////////////////////// Recommendations /////////////////////////////
    public User retrieveUser(String userName) {
        for (var user : userList) {
            if (user.getUsername().equals(userName))
                return user;
        }
        return null;
    }
}
