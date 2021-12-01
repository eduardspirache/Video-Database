package user;

import video.Movie;
import video.Serial;
import video.Show;
import video.ShowList;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static common.Constants.PREMIUM;

public class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private final List<String> ratedMovies = new ArrayList<>();

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    /**
     * Returns username
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns history of watched shows
     */
    public Map<String, Integer> getHistory() {
        return history;
    }

    /**
     * Returns the subscription (basic/premium)
     */
    public String getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * Returns a list of the user's favorite movies
     */
    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    //////////////////////////////// Queries ////////////////////////////////

    /**
     * Returns the number of ratings the user given
     */
    public int getCountRatings() {
        return ratedMovies.size();
    }

    //////////////////////////////// Commands ////////////////////////////////

    /**
     * Checks if the user has seen the show
     */
    public boolean seen(final String show) {
        return history.containsKey(show);
    }

    /**
     * If the show is not in the user's favorites list
     * and the user watched the show, it adds it to the list
     */
    public String favorite(final String show) {
        if (seen(show) && !favoriteMovies.contains(show)) {
            favoriteMovies.add(show);
            return "success -> " + show + " was added as favourite";
        } else if (!seen(show)) {
            return "error -> " + show + " is not seen";
        } else {
            return "error -> " + show + " is already in favourite list";
        }
    }

    /**
     * If the user hasn't seen the show,
     * it marks it as seen and adds it to the
     * history
     */
    public String view(final String show) {
        if (!seen(show)) {
            history.put(show, 1);
        } else {
            int count = history.get(show);
            history.replace(show, count + 1);
        }
        return "success -> " + show + " was viewed with total views of "
                + history.get(show);
    }

    /**
     * If the user has seen the movie and hasn't rated it already,
     * it adds the show to the list of user's rated movies and
     * adds the rating to the movie's ratings list, then recalculates
     * the movie's total rating.
     */
    public String rateMovie(final Movie movie, final double rating) {
        if (seen(movie.getTitle()) && !ratedMovies.contains(movie.getTitle())) {
            movie.setRating(rating);
            ratedMovies.add(movie.getTitle());
            return "success -> " + movie.getTitle() + " was rated with "
                    + rating + " by " + username;
        } else if (!seen(movie.getTitle())) {
            return "error -> " + movie.getTitle() + " is not seen";
        } else {
            return "error -> " + movie.getTitle() + " has been already rated";
        }
    }

    /**
     * If the user has seen the serial and hasn't rated the season already,
     * it adds the show to the list of user's rated serials and
     * adds the rating to the season's ratings list, then recalculates
     * the serial's total rating.
     */
    public String rateSerial(final Serial serial, final int season, final double rating) {
        if (seen(serial.getTitle()) && !ratedMovies.contains(serial.getTitle() + "S" + season)) {
            serial.setRating(rating, season);
            ratedMovies.add(serial.getTitle() + "S" + season);
            return "success -> " + serial.getTitle() + " was rated with "
                    + rating + " by " + username;
        } else if (!seen(serial.getTitle())) {
            return "error -> " + serial.getTitle() + " is not seen";
        } else {
            return "error -> " + serial.getTitle() + " has been already rated";
        }
    }

    ///////////////////////////// Recommendations /////////////////////////////

    /**
     * Returns the first show not viewed by the user
     */
    public String standard(final ShowList shows) {
        for (var show : shows.getShowList()) {
            if (!history.containsKey(show.getTitle())) {
                return "StandardRecommendation result: " + show.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * Returns the first and highest rated show not viewed by the user
     */
    public String bestUnseen(final ShowList shows) {
        List<Show> sorted = new ArrayList<>(shows.getShowList());
        sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        for (var video : sorted) {
            if (!history.containsKey(video.getTitle())) {
                return "BestRatedUnseenRecommendation result: " + video.getTitle();
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * Returns the first unseen show from the most popular genre
     * *Premium users only*
     */
    public String popularVideo(final ShowList shows, final UserList userList) {
        if (getSubscriptionType().equals(PREMIUM)) {
            Map<String, Integer> top = shows.getPopularGenres(userList);

            for (String genre : top.keySet()) {
                for (var show : shows.getShowList()) {
                    if (show.getGenres().contains(genre)
                            && !history.containsKey(show.getTitle())) {
                        return "PopularRecommendation result: " + show.getTitle();
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * Returns the show most commonly found in all the users' favorite lists
     * *Premium users only*
     */
    public String favoriteVideo(final ShowList shows, final UserList userList) {
        if (getSubscriptionType().equals(PREMIUM)) {
            Map<String, Integer> top = shows.getFavoriteShows(userList);
            for (String showName : top.keySet()) {
                if (!history.containsKey(showName)) {
                    return "FavoriteRecommendation result: " + showName;
                }
            }
        }
        return "FavoriteRecommendation cannot be applied!";
    }

    /**
     * Returns all the unseen movies from a certain genre
     * *Premium users only*
     */
    public String unseenGenre(final ShowList showList, final String genre) {
        if (getSubscriptionType().equals(PREMIUM)) {
            List<Show> shows = new ArrayList<>(showList.getShowList());
            shows.sort((a, b) -> {
                if (a.getRating() != b.getRating()) {
                    return Double.compare(a.getRating(), b.getRating());
                }
                return a.getTitle().compareTo(b.getTitle());
            });
            // We remove from the list all the shows
            // that are from a different genre
            shows.removeIf(a -> !a.getGenres().contains(genre));
            // We remove the shows that the user has already seen
            shows.removeIf(show -> history.containsKey(show.getTitle()));
            if (shows.size() != 0) {
                List<String> titles = new ArrayList<>();
                for (var show : shows) {
                    titles.add(show.getTitle());
                }
                return "SearchRecommendation result: " + titles;
            }
        }
        return "SearchRecommendation cannot be applied!";
    }
}
