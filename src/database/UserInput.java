package database;

import fileio.UserInputData;
import user.User;

import java.util.ArrayList;
import java.util.Map;

public class UserInput {
    public static User returnUser(UserInputData userInput) {
        User user;

        String username = userInput.getUsername();
        String subscription = userInput.getSubscriptionType();
        Map<String, Integer> history = userInput.getHistory();
        ArrayList<String> favorite = userInput.getFavoriteMovies();

        user = new User(username, subscription, history, favorite);
        return user;
    }
}
