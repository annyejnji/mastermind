package sk.tuke.gamestudio.game.consoleui;

import java.util.HashMap;
import java.util.Map;

public class CredentialsManager {
    private static final Map<String, String> userCredentials = new HashMap<>();
    private static String currentUser = null;

    public static boolean login(String username, String password) {
        if (userCredentials.containsKey(username)) {
            boolean has_correct_password = userCredentials.get(username).equals(password);
            if (has_correct_password) {
                currentUser = username;
                return true;
            }
        }
        currentUser = null;
        return false;
    }

    public static boolean register(String username, String password) {
        if (!userCredentials.containsKey(username)) {
            userCredentials.put(username, password);
            return true;
        }
        return false;
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}
