import java.util.LinkedList;
import java.util.Queue;

public class Core {

    private final Queue<Process> processesWithLock;
    private final Queue<Process> processesWithoutLock;
    private int timeWithLock;
    private int timeWithoutLock;
    private final int timeWorkDevice = 2000;

    public Core() {
        processesWithLock = new LinkedList<>();
        processesWithoutLock = new LinkedList<>();
    }

    public void createProcess(int pID, int processTime, boolean interactionWithDevice) {
        processesWithLock.add(new Process(pID, processTime, interactionWithDevice));
        processesWithoutLock.add(new Process(pID, processTime, interactionWithDevice));
    }

    public void planningProcessWithoutLock() {
        Process buffer = processesWithoutLock.poll();
        while (buffer != null) {
            System.out.println("Процесс " + buffer.getpID() + " начал работу");
            if (buffer.isInteractionWithDevice()) {
                System.out.println("Процесс " + buffer.getpID() + " работает с устройством ввода/вывода");
                System.out.println("Планировщик приостановлен");
                timeWithoutLock += timeWorkDevice;
                System.out.println("Планировщик возобновил работу");
            }
            System.out.println("Процесс " + buffer.getpID() + " закончил работу");
            timeWithoutLock += buffer.getProcessTime();
            buffer = processesWithoutLock.poll();
        }
        System.out.println("На выполнение процессов без блокировки ушло " + timeWithoutLock + " единиц времени");
    }

    public void planningProcessWithLock() {
        Process buffer = processesWithLock.poll();
        while (buffer != null) {
            if (buffer.isLock()) {
                timeWithLock += 100;
                if (timeWithLock - buffer.getTimeWithLock() > timeWorkDevice) {
                    System.out.println("Процесс " + buffer.getpID() + " закончил работу с устройством ввода/вывода, процесс разблокирован");
                    System.out.println("Процесс " + buffer.getpID() + " закончил работу");
                    timeWithLock += buffer.getProcessTime();
                } else {
                    processesWithLock.add(buffer);
                }
                buffer = processesWithLock.poll();
                continue;
            }
            System.out.println("Процесс " + buffer.getpID() + " начал работу");
            if (buffer.isInteractionWithDevice()) {
                System.out.println("Процесс " + buffer.getpID() + " работает с устройством ввода/вывода");
                System.out.println("Блокирование процесса");
                buffer.setLock(true);
                buffer.setTimeWithLock(timeWithLock);
                processesWithLock.add(buffer);
            } else {
                timeWithLock += buffer.getProcessTime();
                for (Process process : processesWithLock) {
                    if (process.isLock()) {
                        if (timeWithLock - process.getTimeWithLock() > timeWorkDevice) {
                            System.out.println("Процесс " + process.getpID() + " закончил работу с устройством ввода/вывода, процесс разблокирован");
                            System.out.println("Процесс " + process.getpID() + " закончил работу");
                            timeWithLock += process.getProcessTime();
                            processesWithLock.remove(process);
                            break;
                        }
                    }
                }
                System.out.println("Процесс " + buffer.getpID() + " закончил работу");
            }
            buffer = processesWithLock.poll();
        }
        System.out.println("На выполнение процессов c блокировкой ушло " + timeWithLock + " единиц времени");
    }
}
