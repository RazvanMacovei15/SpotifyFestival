package com.example.spotifyfestival.ConcertsAndFestivals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GenericGraph<T> {
    private final Map<T, List<T>> adjacencyList;


    public GenericGraph(Map<T, List<T>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public void addNode(T node) {
        if (adjacencyList.containsKey(node)) {
            return;
        }
        adjacencyList.put(node, new LinkedList<>());
    }

    public void addEdge(T a, T b){
        adjacencyList.get(a).add(b);
        adjacencyList.get(b).add(a);
    }

    public List<T> getNeighbours(T node){
        return adjacencyList.get(node);
    }

    public void countVertices(){
        System.out.println("Total number of vertices: " + adjacencyList.keySet().size());
    }

    public void containsVertex(T node){
        if(adjacencyList.containsKey(node)){
            System.out.println("The graph contains " + node + " as a vertex!");
        }else{
            System.out.println("The graph does not contain " + node + " as a vertex!");
        }
    }

    public void containsEdge(T nodeOne, T nodeTwo){
        if(adjacencyList.get(nodeOne).contains(nodeTwo)){
            System.out.println("The graph has an edge between " + nodeOne + " and " + nodeTwo + "!");
        }else{
            System.out.println("The graph doesn't have an edge between " + nodeOne + " and " + nodeTwo + "!");
        }
    }
}
