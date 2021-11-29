package user;

import video.Movie;

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

    //////////////////////////////// Queries ////////////////////////////////
    public List<User> sortByRating(int n, String sortType) {
        List<User> sorted = userList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(User::getCountRatings));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getCountRatings(),
                    a.getCountRatings()));
        }
        return sorted.subList(0, n - 1);
    }
}
