package system;

public class MenuDisplay {

    public void showHeader(String title) {
        System.out.println("\n====================================");
        System.out.println("  📌 " + title.toUpperCase());
        System.out.println("====================================");
    }

    public void showMenu() {
        showHeader(" Health Tracking System ");
        System.out.println("---------------------");
        System.out.println("📍 MAIN MENU");
        System.out.println("---------------------");
        System.out.println("1️⃣  📊 Track");
        System.out.println("2️⃣  🎯 Set a Goal");
        System.out.println("3️⃣  🏆 Manage Challenges");
        System.out.println("4️⃣  🔄 Reset My Progress");
        System.out.println("0️⃣  ❌ Exit");

        System.out.print("\n➡️  Enter your choice: ");
    }

    public void showTrackingOptions() {
        showSection("Tracking Options");
        System.out.println("1. Water Intake");
        System.out.println("2. Sleep");
        System.out.println("3. Exercise");
        System.out.println("4. Calories");
        System.out.println("5. Weight");
        System.out.println("6. Steps");
        System.out.print("\n➡️  Enter your tracking option: ");
    }

    public void showSection(String title) {
        System.out.println("\n------------------------------");
        System.out.println("  ✨ " + title);
        System.out.println("------------------------------");
    }

    public void showSuccess(String message) {
        System.out.println("\n✅ " + message);
    }

    public void showError(String message) {
        System.out.println("\n❌ " + message);
    }

    public void showDivider() {
        System.out.println("----------------------------------");
    }
}
