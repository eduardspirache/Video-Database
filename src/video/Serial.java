package video;

import entertainment.Season;
import fileio.ShowInput;

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

    // Getters and toString
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }

    // Queries

    @Override
    public double getRating() {
        double avg = 0;
        int count = 0;
        for (var season : seasons) {
            List<Double> ratings = season.getRatings();
            for (Double rating : ratings) {
                avg += rating;
                count++;
            }
        }
        return avg / (double)count;
    }
}
