package com.gytmy.utils;

import java.awt.Color;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import com.gytmy.labyrinth.view.Cell;

/**
 * 
 * The FileTree class is a subclass of JTree, used to display a file system as a
 * tree structure.
 * It extends JTree and implements TreeCellRenderer to customize the appearance
 * of the tree nodes.
 * The FileTree class sets some properties to enhance the usability of the
 * JTree.
 * The getCellRenderer() method overrides the default TreeCellRenderer to
 * customize the rendering of the tree nodes.
 * It sets the label text to the name of the file or directory, and sets the
 * label background and foreground colors
 * based on whether the node is selected or not.
 */
public class FileTree extends JTree {

    private static final Color BACKGROUND_COLOR = Cell.PATH_COLOR;
    private static final Color FOREGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color SELECTION_COLOR = Cell.INITIAL_CELL_COLOR;

    public FileTree(String rootPath) {
        super(new FileSystemTreeModel(new File(rootPath)));

        setBackground(BACKGROUND_COLOR);

        setScrollsOnExpand(true);
        setShowsRootHandles(true);

        addTreeSelectionListener(e -> repaint());

        setCellRenderer(getCellRenderer());
    }

    @Override
    public TreeCellRenderer getCellRenderer() {
        return (tree, value, selected, expanded, leaf, row, hasFocus) -> {

            JLabel label = new JLabel(((File) value).getName());
            label.setOpaque(true);
            label.setForeground(FOREGROUND_COLOR);

            if (selected) {
                label.setBackground(SELECTION_COLOR);
            } else {
                label.setBackground(BACKGROUND_COLOR);
            }

            return label;
        };
    }

    public String getSelectedFilePath() {
        System.out.println(getSelectionPath().getLastPathComponent().toString());
        return getSelectionPath().getLastPathComponent().toString();
    }
}
