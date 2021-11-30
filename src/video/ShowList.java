package video;

import entertainment.Genre;
import user.UserList;

import java.util.*;

import static common.Constants.ASCENDING;
import static java.util.stream.Collectors.toMap;

public class ShowList {
    private final List<Show> showList;


    // Constructor
    public ShowList(List<Show> showList) {
        this.showList = showList;
    }

    // Getter
    public List<Show> getShowList() {
        return showList;
    }

    //////////////////////////////// Queries ////////////////////////////////
    public List<Show> sortQuery(String criteria, String sortType,
                                UserList userList, int year, String genre) {
        List<Show> tempList = switch (criteria) {
            case "ratings" -> sortByRating(sortType);
            case "favorite" -> sortByFavorite(sortType, userList);
            case "longest" -> sortByDuration(sortType);
            default -> sortByViews(sortType, userList);
        };

        if (year != 0)
            tempList.removeIf(a -> a.getYear() != year);
        if (genre != null)
            tempList.removeIf(a -> !a.getGenres().contains(genre));
        return tempList;
    }

    public List<Show> sortByRating(String sortType) {
        List<Show> sorted = new ArrayList<>(this.getShowList());
        sorted.removeIf(a -> a.getRating() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getRating() != b.getRating())
                    return Double.compare(a.getRating(), b.getRating());
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getRating() != b.getRating())
                    return Double.compare(b.getRating(), a.getRating());
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    public List<Show> sortByViews(String sortType, UserList userList) {
        List<Show> sorted = new ArrayList<>(this.getShowList());
        sorted.removeIf(a -> a.getViews(userList) == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getViews(userList) != b.getViews(userList))
                    return Integer.compare(a.getViews(userList), b.getViews(userList));
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getViews(userList) != b.getViews(userList))
                    return Integer.compare(b.getViews(userList), a.getViews(userList));
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    public List<Show> sortByFavorite(String sortType, UserList userList) {
        List<Show> sorted = new ArrayList<>(this.getShowList());
        sorted.removeIf(a -> a.getFavorite(userList) == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getFavorite(userList) != b.getFavorite(userList))
                    return Integer.compare(a.getFavorite(userList), b.getFavorite(userList));
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getFavorite(userList) != b.getFavorite(userList))
                    return Integer.compare(b.getFavorite(userList), a.getFavorite(userList));
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    public List<Show> sortByDuration(String sortType) {
        List<Show> sorted = new ArrayList<>(this.getShowList());
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.getDuration() != b.getDuration())
                    return Integer.compare(a.getDuration(), b.getDuration());
                return a.getTitle().compareTo(b.getTitle());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.getDuration() != b.getDuration())
                    return Integer.compare(b.getDuration(), a.getDuration());
                return b.getTitle().compareTo(a.getTitle());
            });
        }
        return sorted;
    }

    ///////////////////////////// Recommendations /////////////////////////////
    public Map<String, Integer> getPopularGenres(UserList userList) {
        Map<String, Integer> top = new LinkedHashMap<>();

        for (Genre genre : Genre.values()) {
            String comparableGenre = Genres.returnGenre(genre);
            int views = 0;
            for (var show : showList) {
                if (show.getGenres().contains(comparableGenre))
                    views += show.getViews(userList);
            }
            top.put(comparableGenre, views);
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
