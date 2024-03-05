package com.example.spotifyfestival.tree;

import com.example.spotifyfestival.database.entities.pojo.Entity;

public class Tree<T extends Entity> {
    private final TreeNode<T> root;

    public Tree(T data) {
        this.root = new TreeNode<>(data);
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void printTree(Tree<T> tree) {
        printTreeRecursive(tree.getRoot(), 0);
    }

    private void printTreeRecursive(TreeNode<T> node, int depth) {
        if (node == null) {
            return;
        }

        // Print the node's data with an indent based on the depth
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("    "); // Two spaces per depth level
        }

        T concert = node.getData(); // Get the Concert object
        System.out.println(indent.toString() + concert.toString());

        // Recursively print the children
        for (TreeNode<T> child : node.getChildren()) {
            printTreeRecursive(child, depth + 1);
        }
    }
}