package video;

import java.util.ArrayList;
import java.util.Comparator;

import static common.Constants.ASCENDING;

public class ShowList {
    private ArrayList<Show> showList = new ArrayList<>();

    // Constructor
    public ShowList(ArrayList<Show> showList) {
        this.showList = showList;
    }

    // Getter
    public ArrayList<Show> getShowList() {
        return showList;
    }

    // Queries
    public ArrayList<Show> sortByRating(String sortType) {
        ArrayList<Show> sorted = showList;
        sorted.removeIf(a -> a.getRating() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Show::getRating));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        }
        return sorted;
    }

    public ArrayList<Show> sortByFavorite(String sortType) {
        ArrayList<Show> sorted = showList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Show::getFavorite));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getFavorite(), a.getFavorite()));
        }
        return sorted;
    }
}
