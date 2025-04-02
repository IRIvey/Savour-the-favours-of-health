package storage;

import user.User;
import challenge.ChallengeTracker;
import java.io.Serializable;

public class UserDataWrapper implements Serializable {
    private final User user;
    private final ChallengeTracker tracker;

    public UserDataWrapper(User user, ChallengeTracker tracker) {
        this.user = user;
        this.tracker = tracker;
    }

    public User getUser() {
        return user;
    }

    public ChallengeTracker getTracker() {
        return tracker;
    }
}

