package video;

import user.User;
import user.UserList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Show {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;
    protected int duration;
    private double rating;

    // Constructor
    public Show(final String title, final int year,
                final ArrayList<String> cast,
                final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.rating = 0.0;
    }

    // Getters


    public int getDuration() {
        return duration;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public double getRating() {
        return rating;
    }

    // Setters
    public void setRating(double finalRating) {
        this.rating = finalRating;
    }

    // Methods for Queries

    // Iterates through the user list and checks
    // how many users have the show set as favorite
    public int getFavorite(UserList userList) {
        int count = 0;
        for (User user : userList.getUserList())
            for (var movie : user.getFavoriteMovies())
                if (movie.equals(this.getTitle()))
                    count++;
        return count;
    }

    // Iterates through the user list and checks
    // if the show is present in the user's history.
    // If it is, it increments the number of views with the
    // number of times the user watched the show
    public int getViews(UserList userList) {
        AtomicInteger noOfViews = new AtomicInteger();
        for (var user : userList.getUserList()) {
            user.getHistory().forEach((a, n) -> {
                if(a.equals(this.getTitle())) {
                    noOfViews.addAndGet(n);
                }
            });
        }
        return noOfViews.get();
    }
}

