package org.leviverkerk;

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
    public String toString() {
        return "Node{" +
                "coordinate=" + coordinate +
                ", currentDisk=" + currentDisk +
                '}';
    }
}
