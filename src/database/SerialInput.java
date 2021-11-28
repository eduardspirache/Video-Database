package database;

import entertainment.Season;
import fileio.SerialInputData;
import video.Serial;

import java.util.ArrayList;

public class SerialInput {
    public static Serial returnSerial(SerialInputData serialInput) {
        Serial serial;

        String title = serialInput.getTitle();
        int year = serialInput.getYear();
        ArrayList<String> cast = serialInput.getCast();
        ArrayList<String> genres = serialInput.getGenres();
        int numberOfSeasons = serialInput.getNumberSeason();
        ArrayList<Season> seasons = serialInput.getSeasons();

        serial = new Serial(title, cast, genres, numberOfSeasons, seasons, year);
        return serial;
    }
}
