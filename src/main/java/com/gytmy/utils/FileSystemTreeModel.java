package com.gytmy.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileSystemTreeModel implements TreeModel {

    private File root;

    public FileSystemTreeModel(File root) {
        this.root = root;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        File file = (File) parent;
        File[] children = file.listFiles();
        Arrays.sort(children);
        return children[index];
    }

    @Override
    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            String[] children = file.list();
            return children != null ? children.length : 0;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isLeaf(Object node) {
        File file = (File) node;
        return file.isFile();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        // Not implemented since we don't allow editing
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File file = (File) parent;
        File[] children = file.listFiles();
        Arrays.sort(children);
        for (int i = 0; i < children.length; i++) {
            if (children[i].equals(child)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener listener) {
        Collections.singletonList(listener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        Collections.emptyList();
    }
}
