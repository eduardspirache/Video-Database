package database;

import fileio.MovieInputData;
import video.Movie;

import java.util.ArrayList;

public class MovieInput {
    public static Movie returnMovie(MovieInputData movieInput) {
        Movie movie;

        String title = movieInput.getTitle();
        int year = movieInput.getYear();
        ArrayList<String> cast = movieInput.getCast();
        ArrayList<String> genres = movieInput.getGenres();
        int duration = movieInput.getDuration();

        movie = new Movie(title, cast, genres, year, duration);
        return movie;
    }
}
