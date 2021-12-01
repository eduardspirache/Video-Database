package user;

import video.Movie;
import video.Serial;

import java.util.ArrayList;
import java.util.List;

import static common.Constants.ASCENDING;

public class UserList {
    private final List<User> userList;

    public UserList(final List<User> userList) {
        this.userList = userList;
    }

    /**
     * To copy the list, make sure to use:
     * List<User> newList = new ArrayList<>(user.getUserList());
     */
    public List<User> getUserList() {
        return userList;
    }

    //////////////////////////////// Commands ////////////////////////////////

    /**
     * Helper function to call favorite and view methods
     */
    public String simpleCommands(final String command, final String userName, final String show) {
        for (var user : userList) {
            if (user.getUsername().equals(userName)) {
                if (command.equals("favorite")) {
                    return user.favorite(show);
                } else {
                    return user.view(show);
                }
            }
        }
        return null;
    }

    /**
     * Receives the username of the user that wants to rate the movie
     * and calls the function
     */
    public String rateMovie(final String userName, final Movie movie, final double rating) {
        for (var user : userList) {
            if (user.getUsername().equals(userName)) {
                return user.rateMovie(movie, rating);
            }
        }
        return null;
    }

    /**
     * Receives the username of the user that wants to rate the serial
     * and calls the function
     */
    public String rateSerial(final String userName, final Serial serial,
                             final int season, final double rating) {
        for (var user : userList) {
            if (user.getUsername().equals(userName)) {
                return user.rateSerial(serial, season, rating);
            }
        }
        return null;
    }

    //////////////////////////////// Queries ////////////////////////////////

    /**
     * Sorts all the users by the number of ratings accorded
     */
    public List<User> sortByRating(final int n, final String sortType) {
        List<User> sorted = new ArrayList<>(userList);
        sorted.removeIf(a -> a.getCountRatings() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getCountRatings() != b.getCountRatings()) {
                    return Integer.compare(a.getCountRatings(), b.getCountRatings());
                }
                return a.getUsername().compareTo(b.getUsername());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getCountRatings() != b.getCountRatings()) {
                    return Integer.compare(b.getCountRatings(), a.getCountRatings());
                }
                return b.getUsername().compareTo(a.getUsername());
            });
        }
        if (n < sorted.size()) {
            return sorted.subList(0, n);
        } else {
            return sorted;
        }
    }

    ///////////////////////////// Recommendations /////////////////////////////

    /**
     * Receives the username as parameter and retrieves it from the user list
     */
    public User retrieveUser(final String userName) {
        for (var user : userList) {
            if (user.getUsername().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
