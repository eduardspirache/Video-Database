package video;

import entertainment.Genre;
import user.UserList;

import java.util.*;

import static common.Constants.ASCENDING;
import static java.util.stream.Collectors.toMap;

public class ShowList {
    private List<Show> showList;


    // Constructor
    public ShowList(List<Show> showList) {
        this.showList = showList;
    }

    // Getter
    public List<Show> getShowList() {
        return showList;
    }

    //////////////////////////////// Queries ////////////////////////////////
    public List<Show> sortByRating(String sortType) {
        List<Show> sorted = showList;
        sorted.removeIf(a -> a.getFinalRating() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a,b) -> {
                if(a.getFinalRating() != b.getFinalRating())
                    return Double.compare(a.getFinalRating(), b.getFinalRating());
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if(a.getFinalRating() != b.getFinalRating())
                    return Double.compare(b.getFinalRating(), a.getFinalRating());
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    public List<Show> sortByViews(String sortType, UserList userList) {
        List<Show> sorted = showList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(a -> a.getViews(userList)));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getViews(userList), a.getViews(userList)));
        }
        return sorted;
    }

    ///////////////////////////// Recommendations /////////////////////////////
    public Map<String, Integer> getPopularGenres(Genre genres, UserList userList) {
        Map<String, Integer> top = new LinkedHashMap<>();

        for (Genre genre : Genre.values()) {
            // We make a list with all the shows that
            // contain the genre we are looking for
            List<Show> specificGenreShow = getShowList();
            specificGenreShow.removeIf(a -> !a.getGenres()
                    .contains(genre.toString()));
            // We iterate through all the shows
            // we found in a genre and count the views
            int views = 0;
            for (var show : specificGenreShow)
                views += show.getViews(userList);
            top.put(genre.toString(), views);
        }
        // We sort the map
        return top.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    public Map<String, Integer> getFavoriteShows(UserList userList) {
        Map<String, Integer> top = new LinkedHashMap<>();

        // We retrieve the number of times a show was marked as favorite
        // and store it in a map
        for (var show : showList) {
            int likes = 0;
            for (var user : userList.getUserList())
                if (user.getFavoriteMovies().contains(show.getTitle()))
                    likes++;
            top.put(show.getTitle(), likes);
        }

        // We sort the map
        return top.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }
}
