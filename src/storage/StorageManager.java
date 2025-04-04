package storage;

import user.User;
import challenge.ChallengeTracker;

import java.io.*;

public class StorageManager {
    private static final String FILE_NAME = "user_data.ser";
    private static StorageManager instance;

    private StorageManager() {}

    public static StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    public void save(User user, ChallengeTracker tracker) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(new UserDataWrapper(user, tracker));
            System.out.println("✅ Data saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Failed to save data: " + e.getMessage());
        }
    }

    public UserDataWrapper load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (UserDataWrapper) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠ No saved data found. Starting fresh.");
            return null;
        }
    }
}
