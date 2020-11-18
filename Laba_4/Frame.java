import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class Frame {

    private JFrame frame;
    private JButton createFile;
    private JButton createFolder;
    private JButton deleteObject;
    private JButton copyObject;
    private JButton pasteObject;
    private JButton moveObject;
    private JButton start;
    private Panel panelDisk;
    private JTextField diskField;
    private JTextField sectorField;
    private JTextField fileField;
    private int sizeDisk;
    private int sizeSector;
    private Disk disk;
    private JTree tree;
    private FileManager fileManager;
    private DefaultMutableTreeNode selectedNode;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode buffer;

    public Frame() {
        initialization();
    }

    public void initialization() {
        panelDisk = new Panel();
        panelDisk.setBounds(0, 0, 1000, 600);
        frame = new JFrame();
        start = new JButton("Start");
        start.setBounds(550, 680, 100, 50);

        start.addActionListener(e -> {
            createFile.setEnabled(true);
            createFolder.setEnabled(true);
            deleteObject.setEnabled(true);
            copyObject.setEnabled(true);
            pasteObject.setEnabled(true);
            moveObject.setEnabled(true);
            sizeDisk = Integer.parseInt(diskField.getText());
            sizeSector = Integer.parseInt(sectorField.getText());
            disk = new Disk(sizeDisk, sizeSector);
            fileManager = new FileManager(disk);
            start.setEnabled(false);
            diskField.setEnabled(false);
            sectorField.setEnabled(false);
            panelDisk.setDisk(disk);
            panelDisk.setLayout(null);
            root = new DefaultMutableTreeNode(fileManager.getMainFile(), true);
            tree = new JTree(root);
            tree.updateUI();
            tree.setBounds(0, 0, 170, 500);
            tree.addTreeSelectionListener(e1 -> {
                selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                fileManager.removeSelecting();
                select(selectedNode);
            });
            panelDisk.add(tree);
            frame.repaint();
        });
        createFile = new JButton("Create File");
        createFile.setBounds(1050, 50, 120, 30);
        createFile.addActionListener(e -> {
            if (!selectedNode.getAllowsChildren()) {
                JOptionPane.showMessageDialog(frame, "Некорректное действие (в файл добавлять нельзя)", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            File file = fileManager.addFile("file", Integer.parseInt(fileField.getText()));
            if (file != null) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(file, false);
                selectedNode.add(node);
                tree.updateUI();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Недостаточно места!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        createFolder = new JButton("Create Folder");
        createFolder.setBounds(1050, 130, 120, 30);
        createFolder.addActionListener(e -> {
            if (!selectedNode.getAllowsChildren()) {
                JOptionPane.showMessageDialog(frame, "Некорректное действие (в файл добавлять нельзя)", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            File folder = fileManager.addFolder("folder");
            if (folder != null) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(folder, true);
                selectedNode.add(node);
                tree.updateUI();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Недостаточно места!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteObject = new JButton("Delete");
        deleteObject.setBounds(1050, 210, 120, 30);
        deleteObject.addActionListener(e -> {
            delete(selectedNode);
            frame.repaint();
        });
        pasteObject = new JButton("Paste");
        pasteObject.setBounds(1050, 370, 120, 30);
        pasteObject.addActionListener(e -> {
            paste(selectedNode);
            frame.repaint();
        });
        copyObject = new JButton("Copy");
        copyObject.setBounds(1050, 290, 120, 30);
        copyObject.addActionListener(e -> {
            copy(selectedNode);
            frame.repaint();
        });
        moveObject = new JButton("Move");
        moveObject.setBounds(1050, 450, 120, 30);
        moveObject.addActionListener(e -> {
            if (selectedNode != null && selectedNode.getParent() != null) {
                DefaultMutableTreeNode buffer = selectedNode;
                ((DefaultMutableTreeNode) selectedNode.getParent()).remove(selectedNode);
                MoveSelectionWindow moveSelectionWindow = new MoveSelectionWindow(frame, root);
                DefaultMutableTreeNode parent = moveSelectionWindow.getNode();
                if (parent != null) {
                    parent.add(buffer);
                }
            }
            tree.updateUI();
            frame.repaint();
        });
        JLabel diskText = new JLabel("Disk Size");
        diskText.setBounds(10, 680, 70, 20);
        diskField = new JTextField("8192");
        diskField.setBounds(80, 680, 70, 20);
        JLabel sectorText = new JLabel("Sector Size");
        sectorText.setBounds(10, 700, 70, 20);
        sectorField = new JTextField("16");
        sectorField.setBounds(80, 700, 70, 20);
        JLabel fileText = new JLabel("File Size");
        fileText.setBounds(10, 720, 70, 20);
        fileField = new JTextField("10");
        fileField.setBounds(80, 720, 70, 20);
        createFile.setEnabled(false);
        createFolder.setEnabled(false);
        deleteObject.setEnabled(false);
        copyObject.setEnabled(false);
        pasteObject.setEnabled(false);
        moveObject.setEnabled(false);
        frame.setBounds(0, 0, 1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().add(panelDisk);
        frame.getContentPane().add(createFile);
        frame.getContentPane().add(createFolder);
        frame.getContentPane().add(deleteObject);
        frame.getContentPane().add(copyObject);
        frame.getContentPane().add(pasteObject);
        frame.getContentPane().add(moveObject);
        frame.getContentPane().add(diskText);
        frame.getContentPane().add(diskField);
        frame.getContentPane().add(sectorField);
        frame.getContentPane().add(sectorText);
        frame.getContentPane().add(start);
        frame.getContentPane().add(fileField);
        frame.getContentPane().add(fileText);
        frame.repaint();
    }

    private void select(DefaultMutableTreeNode node) {
        if (node.getUserObject() != null) {
            File file = (File) node.getUserObject();
            Cell cell = file.getCell();
            if (cell != null) {
                fileManager.selectFile(cell);
                frame.repaint();
            }
        }
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getChildCount(); i++) {
                select((DefaultMutableTreeNode) node.getChildAt(i));
            }
        }
    }

    private void delete(DefaultMutableTreeNode node) {
        while (!node.isLeaf()) {
            delete((DefaultMutableTreeNode) node.getChildAt(0));
        }
        if (node.getUserObject() != null) {
            File file = (File) node.getUserObject();
            fileManager.deleteFile(file);
            ((DefaultMutableTreeNode) node.getParent()).remove(node);
            tree.updateUI();
            frame.repaint();
        }
    }

    private void copy(DefaultMutableTreeNode node) {
        if (node != null && node.getParent() != null) {
            buffer = node;
        }
    }

    private DefaultMutableTreeNode clone(DefaultMutableTreeNode node) {
        File file = (File) node.getUserObject();
        Cell cell = fileManager.selectMemory(file.getSize());
        DefaultMutableTreeNode newNode;
        if (cell != null) {
            newNode = new DefaultMutableTreeNode(new File(node.toString(), file.getSize(), cell), node.getAllowsChildren());
            for (int i = 0; i < node.getChildCount(); i++) {
                newNode.add(clone((DefaultMutableTreeNode) node.getChildAt(i)));
            }
            return newNode;
        } else {
            JOptionPane.showMessageDialog(frame, "Недостаточно места!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void paste(DefaultMutableTreeNode parent) {
        if (parent.getAllowsChildren() && buffer != null) {
            parent.add(clone(buffer));
            tree.updateUI();
        }
    }

}
