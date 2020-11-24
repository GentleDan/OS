public class Start {

    public static void main(String[] args) {
        Core core = new Core();
        core.createProcess(1, 10000, true);
        core.createProcess(2, 6785, true);
        core.createProcess(3, 7845, false);
        core.createProcess(4, 1200, true);
        core.createProcess(5, 9822, false);
        core.planningProcessWithoutLock();
        System.out.println("\n");
        core.planningProcessWithLock();
    }
}
