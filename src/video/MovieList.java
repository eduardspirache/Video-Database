package video;

import actor.Actor;

import java.util.ArrayList;
import java.util.Comparator;

import static common.Constants.ASCENDING;

public class MovieList {
    private ArrayList<Movie> movieList = new ArrayList<>();

    // Constructor
    public MovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    // Getter
    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    // Queries
    public ArrayList<Movie> sortByRating(String sortType) {
        ArrayList<Movie> sorted = movieList;
        sorted.removeIf(a -> a.getRating() == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Movie::getRating));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        }
        return sorted;
    }

    public ArrayList<Movie> sortByFavorite(String sortType) {
        ArrayList<Movie> sorted = movieList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Movie::getFavorite));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getFavorite(), a.getFavorite()));
        }
        return sorted;
    }
}
