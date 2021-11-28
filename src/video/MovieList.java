package video;

import user.UserList;

import java.util.List;
import java.util.Comparator;

import static common.Constants.ASCENDING;

public class MovieList {
    private List<Movie> movieList;

    // Constructor
    public MovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    // Getter
    public List<Movie> getMovieList() {
        return movieList;
    }

    // Queries
    public List<Movie> sortByRating(String sortType) {
        List<Movie> sorted = movieList;
        sorted.removeIf(a -> a.getRating() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Movie::getRating));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        }
        return sorted;
    }

    public List<Movie> sortByFavorite(String sortType, UserList userList) {
        List<Movie> sorted = movieList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(a -> a.getFavorite(userList)));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getFavorite(userList), a.getFavorite(userList)));
        }
        return sorted;
    }

    public List<Movie> sortByDuration(String sortType) {
        List<Movie> sorted = movieList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(Movie::getDuration));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getDuration(), a.getDuration()));
        }
        return sorted;
    }

    public List<Movie> sortByViews(String sortType, UserList userList) {
        List<Movie> sorted = movieList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(a -> a.getViews(userList)));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getViews(userList), a.getViews(userList)));
        }
        return sorted;
    }
}
