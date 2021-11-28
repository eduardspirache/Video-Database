package actor;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

import static common.Constants.ASCENDING;

public class ActorList {
    private List<Actor> actorList;

    // Constructor
    public ActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    // Getter
    public List<Actor> getActorList() {
        return actorList;
    }

    // Queries
    public List<Actor> sortByRating(String sortType) {
        List<Actor> sorted = actorList;
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparingDouble(Actor::getRating));
        } else {
            sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        }
        return sorted;
    }

    public List<Actor> sortByAwards(String sortType,
                                         List<String> awards) {
        List<Actor> sorted = actorList;
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

    public List<Actor> sortByDescription(String sortType,
                                              List<String> words) {
        List<Actor> sorted = actorList;
        sorted.removeIf(a -> !a.filterDescription(words));
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparing(Actor::getName));
        } else {
            sorted.sort((a, b) -> b.getName().compareTo(a.getName()));
        }
        return sorted;
    }
}
