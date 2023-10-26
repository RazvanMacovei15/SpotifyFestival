package com.example.spotifyfestival.Tree;

import java.util.List;

public class Tree<T> {
    private TreeNode<T> root;

    public Tree(T data) {
        this.root = new TreeNode<>(data);
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    // Generic method to create a tree with any type
    public static <T> Tree<T> createTree(T rootData) {
        return new Tree<>(rootData);
    }

    public void createTree(List<T> venueList, List<T> concertList, T userLocation){
        Tree<T> canvasTree = createTree(userLocation);

        for(int i = 0; i < venueList.size(); i++){
            TreeNode<T> rootChild = new TreeNode<>(venueList.get(i));
            canvasTree.getRoot().addChild(rootChild);
            for(int j = 0; j < concertList.size(); j++){
                TreeNode<T> concertChild = new TreeNode<>(concertList.get(j));
                rootChild.addChild(concertChild);
            }
        }
    }
}