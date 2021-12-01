package main;

import actor.ActorList;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import database.Database;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;
import user.UserList;
import video.MovieList;
import video.SerialList;
import video.Show;
import video.ShowList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static common.Constants.AWARDS_PLACE;
import static common.Constants.WORDS_PLACE;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        UserList userList = new UserList(Database.getUsers(input));
        ActorList actorList = new ActorList(Database.getActors(input));
        MovieList movieList = new MovieList(Database.getMovies(input));
        SerialList serialList = new SerialList(Database.getSerials(input));
        ShowList showList = new ShowList(Database.returnShowList(movieList, serialList));

        for (var action : input.getCommands()) {
            String output = null;
            if (action.getActionType().equals("command")) {
                if (!action.getType().equals("rating")) {

                    output = userList.simpleCommands(action.getType(),
                            action.getUsername(), action.getTitle());
                } else {

                    if (action.getSeasonNumber() == 0) {
                        for (var movie : movieList.getMovieList()) {
                            if (movie.getTitle().equals(action.getTitle())) {
                                output = userList.rateMovie(
                                        action.getUsername(),
                                        movie, action.getGrade());
                            }
                        }
                    } else {
                        for (var serial : serialList.getSerialList()) {
                            if (serial.getTitle().equals(action.getTitle())) {
                                output = userList.rateSerial(
                                        action.getUsername(), serial,
                                        action.getSeasonNumber(),
                                        action.getGrade());
                            }
                        }
                    }
                }
            } else if (action.getActionType().equals("query")) {
                output = "Query result: ";
                if (action.getObjectType().equals("actors")) {
                    List<String> words = action.getFilters().get(WORDS_PLACE);
                    List<String> awards = action.getFilters().get(AWARDS_PLACE);
                    output += actorList.sortQuery(action.getNumber(),
                            action.getCriteria(), action.getSortType(),
                            awards, words, showList);
                } else if (action.getObjectType().equals("users")) {
                    List<User> sortedList = userList.sortByRating(action.getNumber(),
                            action.getSortType());

                    List<String> outList = new ArrayList<>();
                    for (var user : sortedList) {
                        outList.add(user.getUsername());
                    }
                    output += outList;
                } else {
                    int year = 0;
                    if (action.getFilters().get(0).get(0) != null) {
                        year = Integer.parseInt(action.getFilters().get(0).get(0));
                    }
                    String genre = null;
                    if (action.getFilters().get(1).get(0) != null) {
                        genre = action.getFilters().get(1).get(0);
                    }

                    List<Show> filteredList = showList
                            .sortQuery(action.getCriteria(),
                                    action.getSortType(),
                                    userList, year, genre);

                    // If the query type is a show or movie, we want
                    // to remove from our list the movies or shows.
                    if (action.getObjectType().equals("movies")) {
                        List<Show> serials = new ArrayList<>();
                        for (var show : filteredList) {
                            for (var serial : serialList.getSerialList()) {
                                if (show.getTitle().equals(serial.getTitle())) {
                                    serials.add(show);
                                }
                            }
                        }
                        filteredList.removeAll(serials);
                    } else if (action.getObjectType().equals("shows")) {
                        List<Show> movies = new ArrayList<>();
                        for (var show : filteredList) {
                            for (var movie : movieList.getMovieList()) {
                                if (show.getTitle().equals(movie.getTitle())) {
                                    movies.add(show);
                                }
                            }
                        }
                        filteredList.removeAll(movies);
                    }

                    if (action.getNumber() <= filteredList.size()) {
                        filteredList = filteredList.subList(0, action.getNumber());
                    }

                    // We store the titles in a string list
                    // to be able to easily print them
                    List<String> outList = new ArrayList<>();
                    for (var show : filteredList) {
                        outList.add(show.getTitle());
                    }
                    output += outList;
                }
            } else if (action.getActionType().equals("recommendation")) {
                output = switch (action.getType()) {
                    case "standard" -> userList.retrieveUser(action.getUsername())
                            .standard(showList);
                    case "best_unseen" -> userList.retrieveUser(action.getUsername())
                            .bestUnseen(showList);
                    case "popular" -> userList.retrieveUser(action.getUsername())
                            .popularVideo(showList, userList);
                    case "favorite" -> userList.retrieveUser(action.getUsername())
                            .favoriteVideo(showList, userList);
                    default -> userList.retrieveUser(action.getUsername())
                            .unseenGenre(showList, action.getGenre());
                };

            }
            JSONObject outputObj = fileWriter.writeFile(action.getActionId(), "message", output);
            arrayResult.add(outputObj);
        }
        fileWriter.closeJSON(arrayResult);
    }
}
