public class Process {

    private final int pID;
    private final boolean interactionWithDevice;
    private final int processTime;
    private int timeWithLock;
    private boolean lock;

    public Process(int pID, int processTime, boolean interactionWithDevice) {
        this.pID = pID;
        this.processTime = processTime;
        this.interactionWithDevice = interactionWithDevice;
    }

    public int getpID() {
        return pID;
    }

    public boolean isInteractionWithDevice() {
        return interactionWithDevice;
    }

    public int getProcessTime() {
        return processTime;
    }

    public int getTimeWithLock() {
        return timeWithLock;
    }

    public void setTimeWithLock(int timeWithLock) {
        this.timeWithLock = timeWithLock;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
