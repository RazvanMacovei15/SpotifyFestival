package com.example.spotifyfestival.Tree;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import javafx.collections.ObservableList;

public abstract class Tree<T extends Identifiable>{
    private final TreeNode<T> root;
//    private final NodeCreationListener<T> nodeCreationListener;

    public Tree(T data) {
        this.root = new TreeNode<>(data);
//        this.nodeCreationListener = nodeCreationListener;
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
            indent.append("  "); // Two spaces per depth level
        }

        T concert = node.getData(); // Get the Concert object
        System.out.println(indent.toString() + concert.toString());

        // Recursively print the children
        for (TreeNode<T> child : node.getChildren()) {
            printTreeRecursive(child, depth + 1);
        }
    }
    public abstract ObservableList<T> getConcertsAtVenue(T venue);
    public abstract void drawLocationPin(T item);
    public abstract void drawVenuePin(T item);
    public abstract void drawConcertPin(T item);
    public MyTree<T> createTree(ObservableList<T> venueList, T userLocation){
        MyTree<T> canvasTree = new MyTree<>(userLocation);
        drawLocationPin(userLocation);
        for (T venue : venueList) {
            drawVenuePin(venue);
            TreeNode<T> venueNode = new TreeNode<>(venue);
            canvasTree.getRoot().addChild(venueNode);
            ObservableList<T> concertsAtVenue = getConcertsAtVenue(venue);
            for (T concert : concertsAtVenue) {
                drawConcertPin(concert);
                TreeNode<T> concertNode = new TreeNode<>(concert);
                venueNode.addChild(concertNode);
            }
        }
        return canvasTree;
    }
}