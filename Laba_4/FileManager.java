import java.util.Random;

public class FileManager {

    private final File mainFile;
    private final Disk disk;
    private final FreePlacesList freePlacesList;
    private final Random random;

    public FileManager(Disk disk) {
        random = new Random();
        this.disk = disk;
        freePlacesList = new FreePlacesList(disk);
        mainFile = new File("Root", 1, selectMemory(0));
    }

    public File addFile(String fileName, int size) {
        Cell cell = selectMemory(size);
        if (cell == null) {
            return null;
        }
        return new File(fileName, size, cell);
    }

    public File addFolder(String fileName) {
        Cell cell = selectMemory(0);
        if (cell == null) {
            return null;
        }
        return new File(fileName, 1, cell);
    }

    public void deleteFile(File file) {
        Cell prevCell = null;
        Cell cell = file.getCell();
        while (cell != null) {
            cell.setCellStatus(0);
            if (prevCell != null) {
                prevCell.setNextCell(null);
            }
            prevCell = cell;
            cell = cell.getNextCell();
        }
    }

    public Cell selectMemory(int size) {
        size = size / disk.getSizeSector();
        Cell firstCell = null;
        if ((size + 1) > freePlacesList.getClusters().size()) {
            return null;
        }
        Cell prevCell = null;
        for (int i = 0; i < size + 1; i++) {
            int index = random.nextInt(freePlacesList.getClusters().size());
            Cell buffer = disk.getCells()[freePlacesList.getClusters().get(index)];
            buffer.setCellStatus(1);
            freePlacesList.deleteUselessCluster(index);
            if (prevCell != null) {
                prevCell.setNextCell(buffer);
            } else {
                firstCell = buffer;
            }
            prevCell = buffer;
        }
        return firstCell;
    }

    public void selectFile(Cell cell) {
        while (cell != null) {
            cell.setCellStatus(2);
            cell = cell.getNextCell();
        }
    }

    public void removeSelecting() {
        for (int i = 0; i < disk.getCells().length; i++) {
            if (disk.getCells()[i].getCellStatus() == 2) {
                disk.getCells()[i].setCellStatus(1);
            }
        }
    }

    public File getMainFile() {
        return mainFile;
    }

}
