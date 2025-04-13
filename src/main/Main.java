package main;

import system.HealthTrackingSystem;

public class Main {
    public static void main(String[] args) {
        HealthTrackingSystem system = HealthTrackingSystem.getInstance();
        system.start();
    }
}