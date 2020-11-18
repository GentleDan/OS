public class File {

    private final int size;
    private final String fileName;
    private final Cell cell;

    public File(String fileName, int size, Cell cell) {
        this.fileName = fileName;
        this.size = size;
        this.cell = cell;
    }

    public int getSize() {
        return size;
    }

    public String toString() {
        return fileName;
    }

    public Cell getCell() {
        return cell;
    }
}
