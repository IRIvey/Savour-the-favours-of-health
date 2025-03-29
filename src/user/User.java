package user;

import goal.Goal;
import metric.HealthMetric;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addGoal(Goal newGoal) {
        for (Goal goal : goals) {
            if (goal.getMetric().getClass().equals(newGoal.getMetric().getClass())) {
                goal.updateTargetValue(newGoal.getTargetValue());
                System.out.println("✅ goal.Goal updated for " + goal.getMetric().getName() + " (" + goal.getTargetValue() + " " + goal.getMetric().getUnit() + ")");
                return;
            }
        }
        goals.add(newGoal);
        System.out.println("✅ goal.Goal set for " + newGoal.getMetric().getName() + " (" + newGoal.getTargetValue() + " " + newGoal.getMetric().getUnit() + ")");
    }



    private void checkGoals(HealthData data) {
        for (Goal goal : goals) {
            if (goal.getMetric().getClass().equals(data.getMetric().getClass())) {
                goal.checkIfAchieved(data.getValue());
                if (goal.isAchieved()) {
                    System.out.println("🎉 goal.Goal achieved for " + goal.getMetric().getName() + "!");
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

    public List<HealthData> getHistoryForMetric(HealthMetric metric) {
        return healthHistory.stream()
                .filter(data -> data.getMetric().getClass().equals(metric.getClass()))
                .collect(Collectors.toList());
    }

    public double getTotalRecordedValue(HealthMetric metric) {
        return healthHistory.stream()
                .filter(data -> data.getMetric().getClass().equals(metric.getClass()))
                .mapToDouble(HealthData::getValue)
                .sum();
    }

    public Goal getGoalForMetric(HealthMetric metric) {
        return goals.stream()
                .filter(goal -> goal.getMetric().getClass().equals(metric.getClass()))
                .findFirst()
                .orElse(null);
    }

    public String getName() {
        return name;
    }
}
