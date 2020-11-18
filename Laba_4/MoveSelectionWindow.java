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
        setBounds(0, 0, 900, 900);
        tree.setBounds(0, 0, 400, 500);
        tree.addTreeSelectionListener(e1 -> selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
        add(tree);

        JButton acceptButton = new JButton("Accept");
        acceptButton.setBounds(0, 500, 90, 90);
        acceptButton.addActionListener(e -> {
            accept = true;
            dispose();
        });
        add(acceptButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(0, 600, 90, 90);
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
