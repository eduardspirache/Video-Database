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

    //////////////////////////////// Queries ////////////////////////////////
    public List<Movie> sortByRating(int n, String sortType) {
        List<Movie> sorted = movieList;
        sorted.removeIf(a -> a.getRating() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Movie::getRating));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        }
        return sorted.subList(0, n - 1);
    }

    public List<Movie> sortByFavorite(int n, String sortType, UserList userList) {
        List<Movie> sorted = movieList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(a -> a.getFavorite(userList)));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getFavorite(userList), a.getFavorite(userList)));
        }
        return sorted.subList(0, n - 1);
    }

    public List<Movie> sortByDuration(int n, String sortType) {
        List<Movie> sorted = movieList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(Movie::getDuration));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getDuration(), a.getDuration()));
        }
        return sorted.subList(0, n - 1);
    }

    public List<Movie> sortByViews(int n, String sortType, UserList userList) {
        List<Movie> sorted = movieList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(a -> a.getViews(userList)));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.getViews(userList), a.getViews(userList)));
        }
        return sorted.subList(0, n - 1);
    }
}
