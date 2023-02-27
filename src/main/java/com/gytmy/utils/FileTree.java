package com.gytmy.utils;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

public class FileTree extends JTree {

    private final Color BACKGROUND_COLOR = getBackground();
    private final Color FOREGROUND_COLOR = getForeground();

    public FileTree(String rootPath) {
        super(new FileSystemTreeModel(new File(rootPath)));

        setScrollsOnExpand(true);
        setShowsRootHandles(true);
        // setToggleClickCount(1);

        addTreeSelectionListener(e -> {
            repaint();
        });

        setCellRenderer(getCellRenderer());
    }

    @Override
    public TreeCellRenderer getCellRenderer() {
        return new TreeCellRenderer() {

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                    boolean leaf, int row, boolean hasFocus) {

                JLabel label = new JLabel(((File) value).getName());
                label.setOpaque(true);

                if (selected) {
                    label.setBackground(Color.RED);
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(BACKGROUND_COLOR);
                    label.setForeground(FOREGROUND_COLOR);
                }

                return label;
            }
        };
    }
}
