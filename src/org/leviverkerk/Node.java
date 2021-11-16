package org.leviverkerk;

import java.util.Comparator;

public class Node implements Comparator<Node>, Comparable<Node> {

    private Coordinate coordinate;
    private int cost;
    private Disk currentDisk;

    public Node(Coordinate coordinate, int cost, Disk currentDisk) {
        this.coordinate = coordinate;
        this.cost = cost;
        this.currentDisk = currentDisk;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getCost() {
        return cost;
    }

    public Disk getCurrentDisk() {
        return currentDisk;
    }

    public void setCurrentDisk(Disk currentDisk) {
        this.currentDisk = currentDisk;
    }

    @Override
    public int compare(Node node1, Node node2)
    {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        return 0;
    }

    @Override
    public int compareTo(Node o) {
        if (cost < o.cost)
            return -1;
        if (cost > o.cost)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "coordinate=" + coordinate +
                ", cost=" + cost +
                '}';
    }
}
