package video;

import user.User;
import user.UserList;

import java.util.ArrayList;

public abstract class Show {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;
    protected int duration;
    private double rating;

    public Show(final String title, final int year,
                final ArrayList<String> cast,
                final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.rating = 0.0;
    }

    /**
     * Returns the duration of the show
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the title of the show
     */
    public final String getTitle() {
        return title;
    }

    /**
     * Returns the year of the show
     */
    public final int getYear() {
        return year;
    }

    /**
     * Returns the cast of the show
     */
    public final ArrayList<String> getCast() {
        return cast;
    }

    /**
     * Returns the genres of the show
     */
    public final ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * Returns the total rating of the show
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the total rating of the show
     */
    public void setRating(final double finalRating) {
        this.rating = finalRating;
    }

    //////////////////////////////// Queries ////////////////////////////////

    /**
     * Iterates through the user list and checks
     * how many users have set the show as favorite
     */
    public int getFavorite(final UserList userList) {
        int count = 0;
        for (User user : userList.getUserList()) {
            for (var movie : user.getFavoriteMovies()) {
                if (movie.equals(this.getTitle())) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Iterates through the user list and checks
     * if the show is present in the user's history.
     * If it is, it increments the number of views with the
     * number of times the user watched the show
     */
    public int getViews(final UserList userList) {
        int noOfViews = 0;
        for (var user : userList.getUserList()) {
            for (String showName : user.getHistory().keySet()) {
                if (showName.equals(this.getTitle())) {
                    noOfViews += user.getHistory().get(showName);
                }
            }
        }
        return noOfViews;
    }
}

