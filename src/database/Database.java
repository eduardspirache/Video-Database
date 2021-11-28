package database;

import actor.Actor;
import fileio.*;
import user.Action;
import user.User;
import video.Movie;
import video.Serial;

import java.util.List;

public class Database {
    public static List<Actor> getActors(Input input) {
        List<Actor> actors = null;
        for (var actor : input.getActors())
            actors.add(ActorInput.returnActor(actor));
        return actors;
    }

    public static List<User> getUsers(Input input) {
        List<User> users = null;
        for (var user : input.getUsers())
                users.add(UserInput.returnUser(user));
        return users;
    }

    public static List<Movie> getMovies(Input input) {
        List<Movie> movies = null;
        for (var movie : input.getMovies())
            movies.add(MovieInput.returnMovie(movie));
        return movies;
    }

    public static List<Serial> getSerials(Input input) {
        List<Serial> serials = null;
        for(var serial : input.getSerials())
            serials.add(SerialInput.returnSerial(serial));
        return serials;
    }
}
