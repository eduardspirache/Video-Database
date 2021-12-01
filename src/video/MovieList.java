package video;


import java.util.List;

public class MovieList {
    private final List<Movie> movieList;

    // Constructor
    public MovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    // Getter
    public List<Movie> getMovieList() {
        return movieList;
    }
}
