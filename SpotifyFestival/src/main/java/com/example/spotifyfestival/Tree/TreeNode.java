package com.example.spotifyfestival.Tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
    private T data;
    private List<TreeNode<T>> children;

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
}
