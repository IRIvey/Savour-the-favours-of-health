package reset;

import user.User;
import challenge.ChallengeTracker;
import storage.StorageManager;

public class ResetManager {

    public static void resetUserData(User user) {
        user.clearAllData();
    }

    public static void resetChallenges() {
        ChallengeTracker.getInstance().clearAll();
    }

    public static void resetAll(User user) {
        resetUserData(user);
        resetChallenges();

        StorageManager.save(user, ChallengeTracker.getInstance());
    }
}
