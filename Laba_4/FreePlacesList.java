import java.util.ArrayList;

public class FreePlacesList {

    private final ArrayList<Integer> clusters;

    public FreePlacesList(Disk disk) {
        this.clusters = new ArrayList<>();
        for (int i = 0; i < disk.getCells().length; i++) {
            if (disk.getCells()[i].getCellStatus() == 0) {
                clusters.add(i);
            }
        }
    }

    public ArrayList<Integer> getClusters() {
        return clusters;
    }

    public void deleteUselessCluster(int value) {
        clusters.remove(value);
    }

}
