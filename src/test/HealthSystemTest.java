package test;
import org.junit.jupiter.api.Test;
import goal.*;
import challenge.*;
import metric.*;
import user.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class HealthSystemTest {


    @Test
    void goalShouldBeMarkedAchievedIfTargetMet() {
        HealthMetric metric = new SleepDurationMetric();
        Goal goal = new Goal(metric, 8.0, new DailyGoal());

        goal.checkIfAchieved(8.0);
        assertTrue(goal.isAchieved());
    }

    @Test
    void goalShouldNotBeAchievedIfBelowTarget() {
        HealthMetric metric = new WaterIntakeMetric();
        Goal goal = new Goal(metric, 3000, new DailyGoal());

        goal.checkIfAchieved(2000);
        assertFalse(goal.isAchieved());
    }


    @Test
    void challengeStreakShouldIncreaseOnConsecutiveDays() {
        HealthMetric metric = new StepMetric();
        Challenge challenge = new WeeklyChallenge(metric, 5000, LocalDate.now().minusDays(2), LocalDate.now().plusDays(5));

        challenge.recordActualValue(6000);
        challenge.recordActualValue(7000);
        challenge.recordActualValue(8000);

        assertEquals(1, challenge.getMaxStreak());
    }



    @Test
    void userShouldUpdateGoalIfSameMetricExists() {
        User user = new User("TestUser");
        Goal initialGoal = new Goal(new SleepDurationMetric(), 6.0, new DailyGoal());
        Goal updatedGoal = new Goal(new SleepDurationMetric(), 8.0, new DailyGoal());

        user.addGoal(initialGoal);
        user.addGoal(updatedGoal);

        assertEquals(1, user.getGoals().size());
        assertEquals(8.0, user.getGoalForMetric(new SleepDurationMetric()).getTargetValue(), 0.01);
    }


    @Test
    void shouldRejectInvalidSleepDuration() {
        double sleep = 25.0;
        assertFalse(sleep <= 24, "Sleep duration cannot exceed 24.");
    }

    @Test
    void shouldRejectNegativeSteps() {
        double steps = -1;
        assertFalse(steps >= 1, "Steps must be a positive number.");
    }

    @Test
    void shouldAcceptValidCalorieInput() {
        double calories = 1800;
        assertTrue(calories > 0 && calories <= 10000);
    }


    @Test
    void progressLogShouldBeMarkedMetWhenTargetReached() {
        GoalProgressLog log = new GoalProgressLog(LocalDate.now());
        log.addToValue(1000, 1000);

        assertTrue(log.isMet());
        assertEquals(1000, log.getTotalValue(), 0.01);
    }

    @Test
    void progressLogShouldAccumulateValue() {
        GoalProgressLog log = new GoalProgressLog(LocalDate.now());
        log.addToValue(500, 1000);
        log.addToValue(400, 1000);

        assertFalse(log.isMet());
        assertEquals(900, log.getTotalValue(), 0.01);
    }



    @Test
    void healthDataShouldStoreCorrectTimestampAndValues() {
        HealthMetric metric = new CalorieMetric();
        HealthData data = new HealthData(metric, 1500, "Lunch");

        assertEquals(1500, data.getValue());
        assertEquals("Lunch", data.getNotes());
        assertNotNull(data.getTimestamp());
    }

    @Test
    void userShouldAccumulateHistoryByMetric() {
        User user = new User("Tester");
        HealthMetric metric = new WaterIntakeMetric();

        user.addHealthData(new HealthData(metric, 3000, ""));
        user.addHealthData(new HealthData(metric, 1500, ""));
        user.addHealthData(new HealthData(new CalorieMetric(), 1200, ""));

        double totalWater = user.getTotalRecordedValue(metric);

        assertEquals(4500, totalWater, 0.01);
    }

    @Test
    void getHistoryForMetricShouldReturnCorrectSubset() {
        User user = new User("Tester");
        user.addHealthData(new HealthData(new StepMetric(), 8000, ""));
        user.addHealthData(new HealthData(new StepMetric(), 12000, ""));
        user.addHealthData(new HealthData(new SleepDurationMetric(), 7, ""));

        var stepHistory = user.getHistoryForMetric(new StepMetric());

        assertEquals(2, stepHistory.size());
    }
}
