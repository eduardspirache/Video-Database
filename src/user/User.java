package user;

import entertainment.Genre;
import entertainment.Season;
import video.Movie;
import video.Serial;
import video.Show;
import video.ShowList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static common.Constants.ASCENDING;
import static common.Constants.DESCENDING;

public class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private int countRatings;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.countRatings = 0;
    }

    // Getters and toString
    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public int getCountRatings() {
        return countRatings;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }

    //////////////////////////////// Commands ////////////////////////////////
    public boolean seen(String show) {
        return history.containsKey(show);
    }

    public void favorite(String show) {
        if (seen(show))
            favoriteMovies.add(show);
    }

    public void view(String show) {
        if (!seen(show)) {
            history.put(show, 1);
            return;
        }

        int count = history.get(show);
        history.replace(show, count + 1);
    }

    public void rateMovie(Movie movie, double rating) {
        if (seen(movie.getTitle()))
            movie.setRating(rating);
        this.countRatings++;
    }

    public void rateSerial(Serial serial, int season, double rating) {
        // We check if the user watched the serial
        if (!seen(serial.getTitle()))
            return;

        // We retrieve the season from the list of seasons
        List<Season> seasons = serial.getSeasons();
        Season toRate = seasons.get(season);

        // We copy the list, add the rating and then set the original list
        // to the modified list
        List<Double> listOfRatings = toRate.getRatings();
        listOfRatings.add(rating);
        toRate.setRatings(listOfRatings);
        this.countRatings++;

        // We readjust the show's rating
        serial.setRating();
    }

    ///////////////////////////// Recommendations /////////////////////////////
    public String unseen(ShowList shows) {
        for (var show : shows.getShowList())
            if (history.containsKey(show.getTitle()))
                return show.getTitle();
        return null;
    }

    public String bestUnseen(ShowList shows) {
        List<Show> sorted = shows.sortByRating(DESCENDING);
        for (var video : sorted)
            if (history.containsKey(video.getTitle()))
                return video.getTitle();
        return null;
    }

    public String popularVideo(ShowList shows, UserList userList, Genre genres) {
        Map<String, Integer> top = shows.getPopularGenres(genres, userList);
        for (String showName : top.keySet())
            if (!history.containsKey(showName))
                return showName;
        return null;
    }

    public String favoriteVideo(ShowList shows, UserList userList) {
        Map<String, Integer> top = shows.getFavoriteShows(userList);
        for (String showName : top.keySet())
            if (!history.containsKey(showName))
                return showName;
        return null;
    }

    public List<Show> unseenGenre(ShowList shows, Genre genre) {
        List<Show> showList = shows.sortByRating(DESCENDING);
        // We remove from the list all the shows
        // that are from a different genre
        showList.removeIf(a -> !a.getGenres().contains(genre.toString()));
        // We remove the shows that the user has already seen
        for (var show : showList)
            if (history.containsKey(show.getTitle()))
                showList.remove(show);
        return showList;
    }
}
