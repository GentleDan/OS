import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private Disk disk;

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public void paint(Graphics g) {
        if (disk != null) {
            int size = 20;
            int padding = 200;
            super.paint(g);
            int x = padding;
            int y = padding / 4;

            for (int i = 0; i < disk.getMemoryCapacity(); i++) {
                if (x + size >= getWidth()) {
                    x = padding;
                    y += size;
                }
                switch (disk.getCells()[i].getCellStatus()) {
                    case 0 -> g.setColor(Color.GRAY);
                    case 1 -> g.setColor(Color.BLUE);
                    case 2 -> g.setColor(Color.RED);
                }
                g.fillRect(x, y, size, size);
                g.setColor(Color.black);
                g.drawRect(x, y, size, size);
                x += size;
            }
        }
    }
}
