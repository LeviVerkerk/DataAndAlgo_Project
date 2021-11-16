package org.leviverkerk;

import java.util.Objects;

public class Node implements Comparable<Node>{

    private Coordinate coordinate;
    private Disk currentDisk;

    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.currentDisk = null;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Disk getCurrentDisk() {
        return currentDisk;
    }

    public void setCurrentDisk(Disk currentDisk) {
        this.currentDisk = currentDisk;
    }

    public int getCost() {
        return currentDisk.getCost();
    }

    public int getRadius() {
        return currentDisk.getRadius();
    }

    @Override
    public int compareTo(Node o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return coordinate.equals(node.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }

    @Override
    public String toString() {
        return "Node{" +
                "coordinate=" + coordinate +
                ", currentDisk=" + currentDisk +
                '}';
    }
}
