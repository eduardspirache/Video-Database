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
}
