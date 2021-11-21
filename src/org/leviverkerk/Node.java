package org.leviverkerk;

import java.util.*;

public class Node {

    private NodeValue value;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> neighbouringNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        neighbouringNodes.put(destination, distance);
    }

    public Node(NodeValue value) {
        this.value = value;
    }

    public NodeValue getValue() {
        return value;
    }

    public void setValue(NodeValue value) {
        this.value = value;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getNeighbouringNodes() {
        return neighbouringNodes;
    }

    public void setNeighbouringNodes(Map<Node, Integer> neighbouringNodes) {
        this.neighbouringNodes = neighbouringNodes;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", distance=" + distance +
                '}';
    }
}
