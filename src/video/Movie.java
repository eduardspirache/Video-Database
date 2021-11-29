package video;

import user.User;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Movie extends Show {
    private final int duration;
    private ArrayList<Double> rating;

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
    public int getDuration() {
        return duration;
    }

    // Setters and toString
    public void setRating(double rating) {
        this.rating.add(rating);
        super.setRating(this.getRating());
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }

    ///////////////////////////////// Methods /////////////////////////////////

    public double getRating() {
        if (this.rating == null)
            return 0;

        double avg = 0;
        for (double rate : rating)
            avg += rate;

        return avg / (double) rating.size();
    }

}
