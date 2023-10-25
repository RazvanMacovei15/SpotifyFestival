package com.example.spotifyfestival.ConcertsAndFestivals;

import java.util.ArrayList;
import java.util.List;



public class Tree {
    public static void main(String[] args) {
        // Create a non-binary tree
        TreeNode root = new TreeNode(1);
        TreeNode child1 = new TreeNode(2);
        TreeNode child2 = new TreeNode(3);
        TreeNode child3 = new TreeNode(4);

        root.addChild(child1);
        root.addChild(child2);
        child1.addChild(new TreeNode(5));
        child2.addChild(child3);
        child3.addChild(new TreeNode(6));

        // Traverse and print the tree
        System.out.println("Non-Binary Tree:");
        printTree(root, 0);
    }

    public static void printTree(TreeNode node, int depth) {
        if (node == null) {
            return;
        }

        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }

        System.out.println(indent.toString() + node.getData());
        for (TreeNode child : node.getChildren()) {
            printTree(child, depth + 1);
        }
    }
}
class TreeNode {
    private int data;
    private List<TreeNode> children;

    public TreeNode(int data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public int getData() {
        return data;
    }
}

