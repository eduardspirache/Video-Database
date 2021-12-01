package video;

import entertainment.Genre;
import user.UserList;
import utils.Utils;


import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;

import static common.Constants.ASCENDING;
import static java.util.stream.Collectors.toMap;

public class ShowList {
    private final List<Show> showList;

    public ShowList(final List<Show> showList) {
        this.showList = showList;
    }

    /**
     * To copy the list, make sure to do:
     * List<Show> newList = new ArrayList<>(show.getShowList());
     */
    public List<Show> getShowList() {
        return showList;
    }

    //////////////////////////////// Queries ////////////////////////////////

    /**
     * Helper function to call the different types of query sorting methods
     */
    public List<Show> sortQuery(final String criteria, final String sortType,
                                final UserList userList, final int year, final String genre) {
        List<Show> tempList = switch (criteria) {
            case "ratings" -> sortByRating(sortType);
            case "favorite" -> sortByFavorite(sortType, userList);
            case "longest" -> sortByDuration(sortType);
            default -> sortByViews(sortType, userList);
        };

        if (year != 0) {
            tempList.removeIf(a -> a.getYear() != year);
        }
        if (genre != null) {
            tempList.removeIf(a -> !a.getGenres().contains(genre));
        }
        return tempList;
    }

    /**
     * Sorts the shows by rating (first criteria) and title
     */
    public List<Show> sortByRating(final String sortType) {
        List<Show> sorted = new ArrayList<>(this.getShowList());
        sorted.removeIf(a -> a.getRating() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getRating() != b.getRating()) {
                    return Double.compare(a.getRating(), b.getRating());
                }
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getRating() != b.getRating()) {
                    return Double.compare(b.getRating(), a.getRating());
                }
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    /**
     * Sorts the shows by views (first criteria) and title
     */
    public List<Show> sortByViews(final String sortType, final UserList userList) {
        List<Show> sorted = new ArrayList<>(this.getShowList());
        sorted.removeIf(a -> a.getViews(userList) == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getViews(userList) != b.getViews(userList)) {
                    return Integer.compare(a.getViews(userList), b.getViews(userList));
                }
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getViews(userList) != b.getViews(userList)) {
                    return Integer.compare(b.getViews(userList), a.getViews(userList));
                }
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    /**
     * Sorts the shows by appearances in user's favorites lists (first criteria) and title
     */
    public List<Show> sortByFavorite(final String sortType, final UserList userList) {
        List<Show> sorted = new ArrayList<>(this.getShowList());
        sorted.removeIf(a -> a.getFavorite(userList) == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getFavorite(userList) != b.getFavorite(userList)) {
                    return Integer.compare(a.getFavorite(userList), b.getFavorite(userList));
                }
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getFavorite(userList) != b.getFavorite(userList)) {
                    return Integer.compare(b.getFavorite(userList), a.getFavorite(userList));
                }
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    /**
     * Sorts the shows by duration (first criteria) and title
     */
    public List<Show> sortByDuration(final String sortType) {
        List<Show> sorted = new ArrayList<>(this.getShowList());
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getDuration() != b.getDuration()) {
                    return Integer.compare(a.getDuration(), b.getDuration());
                }
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getDuration() != b.getDuration()) {
                    return Integer.compare(b.getDuration(), a.getDuration());
                }
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    ///////////////////////////// Recommendations /////////////////////////////

    /**
     * Calculates the number of views each genre has and sorts them in descending order
     */
    public Map<String, Integer> getPopularGenres(final UserList userList) {
        Map<String, Integer> top = new LinkedHashMap<>();

        for (Genre genre : Genre.values()) {
            String comparableGenre = Utils.returnGenre(genre);
            int views = 0;
            for (var show : showList) {
                if (show.getGenres().contains(comparableGenre)) {
                    views += show.getViews(userList);
                }
            }
            top.put(comparableGenre, views);
        }
        // We sort the map and return it
        return top.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    /**
     * Calculates the number of times each show was marked as favorite and stores
     * the results in a map, which then sorts in descending order
     */
    public Map<String, Integer> getFavoriteShows(final UserList userList) {
        Map<String, Integer> top = new LinkedHashMap<>();
        for (var show : showList) {
            int likes = 0;
            for (var user : userList.getUserList()) {
                if (user.getFavoriteMovies().contains(show.getTitle())) {
                    likes++;
                }
            }
            top.put(show.getTitle(), likes);
        }

        // We sort the map and return it
        return top.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }
}
