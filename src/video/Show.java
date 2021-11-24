package video;

import java.util.ArrayList;

public abstract class Show {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;

    // Constructor
    public Show(final String title, final int year,
                final ArrayList<String> cast,
                final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    // Getters

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }
}
