import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class MoveSelectionWindow extends JDialog {

    private final JTree tree;
    private DefaultMutableTreeNode selectedNode;
    private boolean accept;

    public MoveSelectionWindow(JFrame owner, DefaultMutableTreeNode root) {
        super(owner, true);
        setLayout(null);
        tree = new JTree(root);
        setBounds(0, 0, 500, 500);
        tree.setBounds(0, 0, 300, 300);
        tree.addTreeSelectionListener(e1 -> selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
        add(tree);

        JButton acceptButton = new JButton("Accept");
        acceptButton.setBounds(0, 400, 80, 60);
        acceptButton.addActionListener(e -> {
            accept = true;
            dispose();
        });
        add(acceptButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(100, 400, 80, 60);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
        tree.updateUI();
        repaint();
        setVisible(true);
    }

    public DefaultMutableTreeNode getNode() {
        if (selectedNode != null && selectedNode.getAllowsChildren() && accept) {
            return selectedNode;
        }
        return null;
    }
}
