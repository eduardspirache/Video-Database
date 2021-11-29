package user;

import entertainment.Season;
import video.Movie;
import video.Serial;
import video.Show;
import video.ShowList;

import java.util.*;

import static common.Constants.*;

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

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }

    //////////////////////////////// Queries ////////////////////////////////
    public int getCountRatings() {
        return ratedMovies.size();
    }

    //////////////////////////////// Commands ////////////////////////////////
    public boolean seen(String show) {
        return history.containsKey(show);
    }

    public String favorite(String show) {
        if (seen(show) && !favoriteMovies.contains(show)) {
            favoriteMovies.add(show);
            return "success -> " + show + " was added as favourite";
        } else if (!seen(show)) {
            return "error -> " + show + " is not seen";
        } else {
            return "error -> " + show + " is already in favourite list";
        }
    }

    public String view(String show) {
        if (!seen(show)) {
            history.put(show, 1);
        } else {
            int count = history.get(show);
            history.replace(show, count + 1);
        }

        return "success -> " + show + " was viewed with total views of "
                + history.get(show);
    }

    public String rateMovie(Movie movie, double rating) {
        if (seen(movie.getTitle()) &&
                !ratedMovies.contains(movie.getTitle())) {
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

    public String rateSerial(Serial serial, int season, double rating) {
        // We check if the user watched the serial and has not rated the season
        if (seen(serial.getTitle()) &&
                !ratedMovies.contains(serial.getTitle() + "S" + season)) {

            // We retrieve the season from the list of seasons
            List<Season> seasons = serial.getSeasons();
            Season toRate = seasons.get(season - 1);

            // We copy the list, add the rating and then set the original list
            // to the modified list
            List<Double> listOfRatings = toRate.getRatings();
            listOfRatings.add(rating);
            toRate.setRatings(listOfRatings);

            // We readjust the show's rating
            serial.setRating();
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
    public String standard(ShowList shows) {
        for (var show : shows.getShowList())
            if (history.containsKey(show.getTitle()))
                return "StandardRecommendation result: " + show.getTitle();
        return "StandardRecommendation cannot be applied!";
    }

    public String bestUnseen(ShowList shows) {
        List<Show> sorted = shows.sortByRating(DESCENDING);
        for (var video : sorted)
            if (history.containsKey(video.getTitle()))
                return "BestRatedUnseenRecommendation result: " +
                        video.getTitle();
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    public String popularVideo(ShowList shows, UserList userList) {
        if (getSubscriptionType().equals(PREMIUM)) {
            Map<String, Integer> top = shows.getPopularGenres(userList);
            for (String showName : top.keySet())
                if (!history.containsKey(showName))
                    return "PopularRecommendation result: " + showName;
        }
        return "PopularRecommendation cannot be applied!";
    }

    public String favoriteVideo(ShowList shows, UserList userList) {
        if (getSubscriptionType().equals(PREMIUM)) {
            Map<String, Integer> top = shows.getFavoriteShows(userList);
            for (String showName : top.keySet())
                if (!history.containsKey(showName))
                    return "FavoriteRecommendation result: " + showName;
        }
        return "FavoriteRecommendation cannot be applied!";
    }

    public String unseenGenre(ShowList shows, String genre) {
        if (getSubscriptionType().equals(PREMIUM)) {
            List<Show> showList = shows.sortByRating(DESCENDING);
            // We remove from the list all the shows
            // that are from a different genre
            showList.removeIf(a -> !a.getGenres().contains(genre));
            // We remove the shows that the user has already seen
            showList.removeIf(show -> history.containsKey(show.getTitle()));

            if (showList.size() != 0) {
                List<String> titles = new ArrayList<>();
                for (var show : showList)
                    titles.add(show.getTitle());
                return "SearchRecommendation result: " + titles;
            }
        }
        return "SearchRecommendation cannot be applied!";
    }
}
