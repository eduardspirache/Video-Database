package video;


import java.util.List;

public class MovieList {
    private final List<Movie> movieList;
    public MovieList(final List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     * To copy the list, make sure to do:
     * List<Movie> newList = new ArrayList<>(movie.getMovieList());
     */
    public List<Movie> getMovieList() {
        return movieList;
    }
}
