import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final List<HealthData> healthHistory;
    private final List<Goal> goals;

    public User(String name) {
        this.name = name;
        this.healthHistory = new ArrayList<>();
        this.goals = new ArrayList<>();
    }

    public void addHealthData(HealthData data) {
        healthHistory.add(data);
        checkGoals(data);
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    private void checkGoals(HealthData data) {
        for (Goal goal : goals) {
            if (goal.getMetric().getName().equals(data.getMetric().getName())) {
                goal.checkIfAchieved(data.getValue()); // Now correctly updates goal status
                if (goal.isAchieved()) {
                    System.out.println("🎉 Goal achieved for " + goal.getMetric().getName() + "!");
                }
            }
        }
    }


    public List<HealthData> getHealthHistory() {
        return new ArrayList<>(healthHistory);
    }

    public List<Goal> getGoals() {
        return new ArrayList<>(goals);
    }

    public String getName() {
        return name;
    }
}
