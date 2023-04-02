package com.gytmy.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileSystemTreeModel implements TreeModel {

    private File root;
    private String regex;

    public FileSystemTreeModel(File root, String regex) {
        this.root = root;
        this.regex = regex;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        File file = (File) parent;
        ArrayList<File> children = new ArrayList<>();

        for (File f : file.listFiles()) {
            if (!f.getName().matches(regex)) {
                children.add(f);
            }
        }

        return children.get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            String[] children = file.list();
            return children != null ? count(children) : 0;
        }

        return 0;
    }

    private int count(String[] children) {
        int total = 0;

        for (String child : children) {
            if (!child.matches(regex)) {
                total++;
            }
        }
        return total;
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
