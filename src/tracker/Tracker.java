package tracker;

import user.User;

public interface Tracker {
    void track(User user, double value, String notes); // Modified to receive input from caller
    void displayStats(User user);
    void checkGoals(User user);
}

