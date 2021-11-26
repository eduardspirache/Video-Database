package actor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static common.Constants.ASCENDING;

public class ActorList {
    private ArrayList<Actor> actorList;

    // Constructor
    public ActorList(ArrayList<Actor> actorList) {
        this.actorList = actorList;
    }

    // Getter
    public ArrayList<Actor> getActorList() {
        return actorList;
    }

    // Queries
    public ArrayList<Actor> sortByRating(String sortType) {
        ArrayList<Actor> sorted = actorList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Actor::getRating));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        }
        return sorted;
    }

    public ArrayList<Actor> sortByAwards(String sortType,
                                         ArrayList<String> awards) {
        ArrayList<Actor> sorted = actorList;
        // We remove the actors that haven't won the prizes the user searched for
        sorted.removeIf(a -> a.noOfAwards(awards) == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingInt(a -> a.noOfAwards(awards)));
        } else {
            sorted.sort((a, b) -> Integer.compare(b.noOfAwards(awards),
                    a.noOfAwards(awards)));
        }
        return sorted;
    }

    public ArrayList<Actor> sortByDescription(String sortType,
                                              ArrayList<String> words) {
        ArrayList<Actor> sorted = actorList;
        sorted.removeIf(a -> !a.filterDescription(words));
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparing(Actor::getName));
        } else {
            sorted.sort((a, b) -> b.getName().compareTo(a.getName()));
        }
        return sorted;
    }
}
