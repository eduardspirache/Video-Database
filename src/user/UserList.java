package user;

import video.Movie;
import video.Serial;
import video.Show;

import java.util.Comparator;
import java.util.List;

import static common.Constants.ASCENDING;

public class UserList {
    private List<User> userList;

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
        List<User> sorted = userList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(User::getCountRatings));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getCountRatings(),
                    a.getCountRatings()));
        }

        if (n <= sorted.size())
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
