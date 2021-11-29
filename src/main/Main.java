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
                        for (var movie : movieList.getMovieList())
                            if (movie.getTitle().equals(action.getTitle()))
                                output = userList.rateMovie(
                                        action.getUsername(),
                                        movie, action.getGrade());
                    } else {
                        for (var serial : serialList.getSerialList())
                            if (serial.getTitle().equals(action.getTitle()))
                                output = userList.rateSerial(
                                        action.getUsername(), serial,
                                        action.getSeasonNumber(),
                                        action.getGrade());
                    }
                }
            } else if (action.getActionType().equals("query")) {
                output = "Query result: ";
                if (action.getObjectType().equals("actors")) {
                    List<String> awards = action.getFilters().get(2);
                    List<String> words = action.getFilters().get(3);

                    output += actorList.sortQuery(action.getNumber(),
                            action.getCriteria(), action.getSortType(),
                            awards, words);
                } else if (action.getObjectType().equals("users")) {
                    List<User> sortedList = userList.sortByRating(action.getNumber(), action.getSortType());

                    List<String> outList = new ArrayList<>();
                    for (var user : sortedList)
                        outList.add(user.getUsername());
                    output += outList;
                } else {
                    int year = 0;
                    if (action.getFilters().get(0).get(0) != null)
                        year = Integer.parseInt(action.getFilters().get(0).get(0));
                    String genre = null;
                    if (action.getFilters().get(1).get(0) != null)
                        genre = action.getFilters().get(1).get(0);

                    List<Show> filteredList = showList
                            .sortQuery(action.getCriteria(),
                                    action.getSortType(),
                                    userList, year, genre);

                    if (action.getObjectType().equals("movies"))
                        filteredList.removeIf(a -> movieList.getMovieList().contains(a.getTitle()));
                    else if (action.getObjectType().equals("shows"))
                        filteredList.removeIf(a -> !serialList.getSerialList().contains(a.getTitle()));

                    if (action.getNumber() <= filteredList.size())
                        filteredList = filteredList.subList(0, action.getNumber() - 1);

                    // We store the titles in a string list
                    // to be able to easily print them
                    List<String> outList = new ArrayList<>();
                    for (var show : filteredList)
                        outList.add(show.getTitle());
                    output += outList;
                }
            } else if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard"))
                    output = userList.retrieveUser(action.getUsername())
                            .standard(showList);
                else if (action.getType().equals("best_unseen"))
                    output = userList.retrieveUser(action.getUsername())
                            .bestUnseen(showList);
                else if (action.getType().equals("popular"))
                    output = userList.retrieveUser(action.getUsername())
                            .popularVideo(showList, userList);
                else if (action.getType().equals("favorite"))
                    output = userList.retrieveUser(action.getUsername())
                            .favoriteVideo(showList, userList);
                else
                    output = userList.retrieveUser(action.getUsername())
                            .unseenGenre(showList, action.getGenre());
            }
            JSONObject outputObj = fileWriter.writeFile(action.getActionId(), "message", output);
            arrayResult.add(outputObj);
        }
        fileWriter.closeJSON(arrayResult);
    }
}
