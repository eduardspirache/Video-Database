package video;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

public class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    /**
     * To copy the list, make sure to do:
     * List<Season> newList = new ArrayList<>(serial.getSeasonList());
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * It retrieves the season from the season list, adds the rating
     * to the season's rating list and the recalculates the total rating
     */
    public void setRating(final double rating, final int season) {
        Season toRate = seasons.get(season - 1);
        toRate.getRatings().add(rating);
        super.setRating(this.getRating());
    }

    ///////////////////////////////// Methods /////////////////////////////////

    /**
     * Calculates the rating for all the seasons (total rating)
     */
    public double getRating() {
        double finalRating = 0.0;

        double avg = 0.0;
        double count = 0.0;
        for (var season : seasons) {
            List<Double> ratings = season.getRatings();
            if (ratings.size() == 0) {
                count++;
            }
            for (Double rating : ratings) {
                avg += rating;
                count++;
            }
        }
        if (count > 0) {
            finalRating = avg / count;
        }
        return finalRating;
    }

    /**
     * Returns the duration of all seasons
     */
    public int getDuration() {
        int duration = 0;
        for (var season : seasons) {
            duration += season.getDuration();
        }
        return duration;
    }
}
