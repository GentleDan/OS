public class Disk {

    private final int sizeSector;
    private final int memoryCapacity;
    private final Cell[] cells;

    public Disk(int sizeDisk, int sizeSector) {
        this.sizeSector = sizeSector;
        memoryCapacity = sizeDisk / sizeSector;
        cells = new Cell[memoryCapacity];
        fillData();
    }

    public void fillData() {
        for (int i = 0; i < memoryCapacity; i++) {
            cells[i] = new Cell(0);
        }
    }

    public int getMemoryCapacity() {
        return memoryCapacity;
    }

    public Cell[] getCells() {
        return cells;
    }

    public int getSizeSector() {
        return sizeSector;
    }
}
