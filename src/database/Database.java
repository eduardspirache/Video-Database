package database;

import actor.Actor;
import fileio.*;
import user.User;
import video.*;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static List<Actor> getActors(Input input) {
        List<Actor> actors = new ArrayList<>();
        for (var actor : input.getActors())
            actors.add(ActorInput.returnActor(actor));
        return actors;
    }

    public static List<User> getUsers(Input input) {
        List<User> users = new ArrayList<>();
        for (var user : input.getUsers())
            users.add(UserInput.returnUser(user));
        return users;
    }

    public static List<Movie> getMovies(Input input) {
        List<Movie> movies = new ArrayList<>();
        for (var movie : input.getMovies())
            movies.add(MovieInput.returnMovie(movie));
        return movies;
    }

    public static List<Serial> getSerials(Input input) {
        List<Serial> serials = new ArrayList<>();
        for (var serial : input.getSerials())
            serials.add(SerialInput.returnSerial(serial));
        return serials;
    }

    public static List<Show> returnShowList(MovieList movieList,
                                            SerialList serialList) {
        List<Show> showList = new ArrayList<>();
        showList.addAll(movieList.getMovieList());
        showList.addAll(serialList.getSerialList());
        return showList;
    }
}
