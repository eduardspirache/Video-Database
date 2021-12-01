package video;

import java.util.ArrayList;

public class Movie extends Show {
    private final int duration;
    private final ArrayList<Double> rating;

    // Constructor
    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        super.duration = duration;
        this.rating = new ArrayList<>();
    }

    // Getters

    /**
     * Returns the duration of the movie
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Adds the rating in the rating list of the movie,
     * then readjusts the total rating
     */
    public void setRating(final double rating) {
        this.rating.add(rating);
        super.setRating(this.getRating());
    }

    ///////////////////////////////// Methods /////////////////////////////////

    /**
     * Iterates through the rating list and
     * returns the total rating
     */
    public double getRating() {
        if (rating != null) {
            double avg = 0.0;
            for (double rate : rating) {
                avg += rate;
            }

            if (rating.size() != 0) {
                return avg / (double) rating.size();
            }
        }
        return 0.0;
    }

}
