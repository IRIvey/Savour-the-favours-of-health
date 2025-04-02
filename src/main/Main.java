package main;

import user.User;
import system.HealthTrackingSystem;
import storage.StorageManager;
import storage.UserDataWrapper;
import challenge.ChallengeTracker;

public class Main {
    public static void main(String[] args) {
        StorageManager storage = StorageManager.getInstance();
        UserDataWrapper data = storage.load();

        User user;
        ChallengeTracker tracker;

        if (data != null) {
            user = data.getUser();
            tracker = data.getTracker();
        } else {
            user = new User("DefaultUser");
            tracker = ChallengeTracker.getInstance();
        }

        HealthTrackingSystem system = HealthTrackingSystem.getInstance();
        system.start(user, tracker);


        storage.save(user, tracker);
    }
}
