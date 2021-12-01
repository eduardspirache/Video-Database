package actor;

import video.Show;
import video.ShowList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Actor {
    private String name;
    private String careerDescription;
    private Double rating;
    private ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    // Constructor
    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.rating = 0.0;
    }

    // Getters and toString
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }

    // Methods for Queries
    public Double getRating(ShowList showList) {
        double newRating = 0.0;
        double count = 0.0;
        List<Show> list = showList.getShowList();
        for (var film : filmography) {
            for (var show : list)
                if (show.getTitle().equals(film) && show.getRating() > 0) {
                    newRating += show.getRating();
                    count++;
                }
        }
        if (count != 0)
            return newRating / count;
        return 0.0;
    }

    // Returns the number of searched awards
    // if it doesn't contain all the awards we searched
    // it returns zero
    public int noOfAwards(List<String> searchedAwards) {
        int count = 0;
        for (String searchedAward : searchedAwards) {
            for (ActorsAwards actorAward : awards.keySet()) {
                if (actorAward.toString().equals(searchedAward)) {
                    count++;
                }
            }
        }
        int sumAwards = 0;
        for (ActorsAwards award : awards.keySet()) {
            sumAwards += awards.get(award);
        }

        if(count == searchedAwards.size())
            return sumAwards;
        return 0;
    }

    public boolean filterDescription(List<String> words) {
        int count = 0;
        for (String word : words) {
            Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(getCareerDescription());
            if (matcher.find()) {
                count++;
            }
        }
        return count == words.size();
    }
}
