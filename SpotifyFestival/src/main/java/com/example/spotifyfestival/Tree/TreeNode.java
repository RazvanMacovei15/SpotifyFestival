package com.example.spotifyfestival.Tree;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T extends Entity> {
    protected T data;
    protected List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void addChild(TreeNode<T> child) {
        children.add(child);
    }

    public void removeChild(TreeNode<T> child) {
        if (child.children != null) {
            child.children.clear();
        }
        children.remove(child);
    }

    public void clearChildren() {
        children.clear();
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                ", children=" + children +
                '}';
    }

}
