package video;

import entertainment.Genre;

public class Genres {
    public static String returnGenre(Genre genre) {
        String myGenre = genre.toString();
        return switch (myGenre) {
            case "TV_MOVIE" -> "TV Movie";
            case "DRAMA" -> "Drama";
            case "FANTASY" -> "Fantasy";
            case "COMEDY" -> "Comedy";
            case "FAMILY" -> "Family";
            case "SCI_FI_FANTASY" -> "Sci-Fi & Fantasy";
            case "CRIME" -> "Crime";
            case "ANIMATION" -> "Animation";
            case "SCIENCE_FICTION" -> "Science Fiction";
            case "ACTION" -> "Action";
            case "HORROR" -> "Horror";
            case "MYSTERY" -> "Mystery";
            case "WESTERN" -> "Western";
            case "ADVENTURE" -> "Adventure";
            case "ACTION_ADVENTURE" -> "Action & Adventure";
            case "ROMANCE" -> "Romance";
            case "THRILLER" -> "Thriller";
            case "KIDS" -> "Kids";
            case "HISTORY" -> "History";
            default -> "";
        };
    }
}
