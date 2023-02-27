package com.gytmy.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileSystemTreeModel implements TreeModel {

    private File root;
    private List<TreeModelListener> listeners = Collections.emptyList();

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
        listeners = Collections.singletonList(listener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        listeners = Collections.emptyList();
    }

    public Enumeration<?> getChildren(Object parent) {
        final File file = (File) parent;
        if (file.isDirectory()) {
            final String[] children = file.list();

            for (String str : children) {
                System.out.println(str);
            }

            if (children != null) {
                Arrays.sort(children);
                return new Enumeration<String>() {
                    int i = 0;

                    @Override
                    public boolean hasMoreElements() {
                        return i < children.length;
                    }

                    @Override
                    public String nextElement() {
                        return children[i++];
                    }
                };
            }
        }
        return Collections.emptyEnumeration();
    }

    public void fireTreeStructureChanged() {
        TreeModelEvent e = new TreeModelEvent(this, new Object[] { root });
        for (TreeModelListener l : listeners) {
            l.treeStructureChanged(e);
        }
    }
}
