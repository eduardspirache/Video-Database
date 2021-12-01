package actor;

import video.ShowList;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import static common.Constants.ASCENDING;

public class ActorList {
    private final List<Actor> actorList;

    public ActorList(final List<Actor> actorList) {
        this.actorList = actorList;
    }

    /**
     * To copy the list, make sure to do:
     * List<Actor> newList = new ArrayList<>(actor.getActorList());
     */
    public List<Actor> getActorList() {
        return actorList;
    }

    //////////////////////////////// Queries ////////////////////////////////

    /**
     * A helper function to call all the actor's query functions
     */
    public String sortQuery(final int n, final String criteria, final String sortType,
                            final List<String> awards, final List<String> words,
                            final ShowList showList) {
        List<Actor> tempList;
        if (criteria.equals("average")) {
            tempList = sortByRating(sortType, showList);
        } else if (criteria.equals("awards") && awards != null) {
            tempList = sortByAwards(sortType, awards);
        } else if (criteria.equals("filter_description") && words != null) {
            tempList = sortByDescription(sortType, words);
        } else {
            return "[]";
        }

        /**
         * We check if the given number of actors to show
         * is larger than the list itself
         */
        if (n <= tempList.size()) {
            tempList = tempList.subList(0, n);
        }

        List<String> output = new ArrayList<>();
        for (var actor : tempList) {
            output.add(actor.getName());
        }
        return "" + output;
    }

    /**
     * Sorts all the actors by the rating recieved by calling the function
     * actor.getRating(showList)
     */
    public List<Actor> sortByRating(final String sortType, final ShowList showList) {
        List<Actor> sorted = new ArrayList<>(actorList);
        sorted.removeIf(a -> a.getRating(showList) == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (!a.getRating(showList).equals(b.getRating(showList))) {
                    return Double.compare(a.getRating(showList), b.getRating(showList));
                }
                return a.getName().compareTo(b.getName());
            });
        } else {
            sorted.sort((a, b) -> {
                if (!a.getRating(showList).equals(b.getRating(showList))) {
                    return Double.compare(b.getRating(showList), a.getRating(showList));
                }
                return b.getName().compareTo(a.getName());

            });
        }
        return sorted;
    }

    /**
     * Sorts the users that have won certain awards
     * by the number of TOTAL awards and eliminates
     * the actors that haven't won all the awards we
     * are looking for
     */
    public List<Actor> sortByAwards(final String sortType, final List<String> awards) {
        List<Actor> sorted = new ArrayList<>(actorList);
        sorted.removeIf(a -> a.noOfAwards(awards) == 0);
        if (sortType.equals(ASCENDING)) {
            sorted.sort((a, b) -> {
                if (a.noOfAwards(awards) != b.noOfAwards(awards)) {
                    return Integer.compare(a.noOfAwards(awards), b.noOfAwards(awards));
                }
                return a.getName().compareTo(b.getName());
            });
        } else {
            sorted.sort((a, b) -> {
                if (a.noOfAwards(awards) != b.noOfAwards(awards)) {
                    return Integer.compare(b.noOfAwards(awards), a.noOfAwards(awards));
                }
                return b.getName().compareTo(a.getName());
            });
        }
        return sorted;
    }

    /**
     * With the help of actor.filterDescription method,
     * we retrieve all the actors that have all the words
     * we are looking for in their description, and then sort
     * them by name
     */
    public List<Actor> sortByDescription(final String sortType, final List<String> words) {
        List<Actor> sorted = new ArrayList<>(actorList);
        sorted.removeIf(a -> !a.filterDescription(words));
        if (sortType.equals(ASCENDING)) {
            sorted.sort(Comparator.comparing(Actor::getName));
        } else {
            sorted.sort((a, b) -> b.getName().compareTo(a.getName()));
        }
        return sorted;
    }
}
