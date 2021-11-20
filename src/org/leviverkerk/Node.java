package org.leviverkerk;

import java.util.Comparator;
import java.util.Objects;

public class Node implements Comparable<Node>, Comparator<Node> {

    private Coordinate coordinate;
    private Disk currentDisk;
    private int distance;

    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.currentDisk = null;
        this.distance = Integer.MAX_VALUE;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Node o) {
        if (distance != o.getDistance())
            return Integer.compare(distance, o.getDistance());
        if (o.equals(this)) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public int compare(Node o1, Node o2) {
        if (o1.distance != o2.getDistance())
            return Integer.compare(o1.distance, o2.getDistance());
        if (o2.equals(o1)) {
            return 0;
        } else {
            return -1;
        }
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
        if (currentDisk != null) {
            return "Node{" +
                    "coordinate=" + coordinate +
                    ", currentDisk=" + currentDisk +
                    ", distance=" + distance +
                    '}';
        } else {
            return "Node{" +
                    "coordinate=" + coordinate +
                    ", distance=" + distance +
                    '}';
        }

    }
}
