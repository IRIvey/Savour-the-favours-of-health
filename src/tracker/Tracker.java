package tracker;

import user.User;

public interface Tracker {
    void track(User user);
    void displayStats(User user);
    void checkGoals(User user);
}
