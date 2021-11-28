package video;

import user.UserList;

import java.util.List;
import java.util.Comparator;

import static common.Constants.ASCENDING;

public class SerialList {
    private List<Serial> serialList;

    // Constructor
    public SerialList(List<Serial> serialList) {
        this.serialList = serialList;
    }

    // Getter
    public List<Serial> getSerialList() {
        return serialList;
    }

    // Queries

    public List<Serial> sortByRating(String sortType) {
        List<Serial> sorted = serialList;
        sorted.removeIf(a -> a.getRating() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Serial::getRating));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        }
        return sorted;
    }

    public List<Serial> sortByFavorite(String sortType, UserList userList) {
        List<Serial> sorted = serialList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(a -> a.getFavorite(userList)));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getFavorite(userList), a.getFavorite(userList)));
        }
        return sorted;
    }

    public List<Serial> sortByDuration(String sortType) {
        List<Serial> sorted = serialList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(Serial::getDuration));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getDuration(), a.getDuration()));
        }
        return sorted;
    }

    public List<Serial> sortByViews(String sortType, UserList userList) {
        List<Serial> sorted = serialList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(a -> a.getViews(userList)));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getViews(userList), a.getViews(userList)));
        }
        return sorted;
    }

}
