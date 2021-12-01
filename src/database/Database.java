package database;

import actor.Actor;
import fileio.Input;
import user.User;
import utils.Utils;
import video.Movie;
import video.MovieList;
import video.Serial;
import video.Show;
import video.SerialList;
import java.util.ArrayList;
import java.util.List;

public final class Database {

    private Database() {

    }

    /**
     * Adds the actors from input in a list
     */
    public static List<Actor> getActors(final Input input) {
        List<Actor> actors = new ArrayList<>();
        for (var actor : input.getActors()) {
            actors.add(Utils.returnActor(actor));
        }
        return actors;
    }

    /**
     * Adds the users from input in a list
     */
    public static List<User> getUsers(final Input input) {
        List<User> users = new ArrayList<>();
        for (var user : input.getUsers()) {
            users.add(Utils.returnUser(user));
        }
        return users;
    }

    /**
     * Adds the movies from input in a list
     */
    public static List<Movie> getMovies(final Input input) {
        List<Movie> movies = new ArrayList<>();
        for (var movie : input.getMovies()) {
            movies.add(Utils.returnMovie(movie));
        }
        return movies;
    }

    /**
     * Adds the serials from input in a list
     */
    public static List<Serial> getSerials(final Input input) {
        List<Serial> serials = new ArrayList<>();
        for (var serial : input.getSerials()) {
            serials.add(Utils.returnSerial(serial));
        }
        return serials;
    }

    /**
     * Merges the serials and movies in a list of shows
     */
    public static List<Show> returnShowList(final MovieList movieList,
                                            final SerialList serialList) {
        List<Show> showList = new ArrayList<>();
        showList.addAll(movieList.getMovieList());
        showList.addAll(serialList.getSerialList());
        return showList;
    }
}
