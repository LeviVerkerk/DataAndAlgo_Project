package org.leviverkerk;

import java.util.Objects;

public class NodeValue {

    private final Coordinate coordinate;
    private final Disk disk;

    public NodeValue(Coordinate coordinate, Disk disk) {
        this.coordinate = coordinate;
        this.disk = disk;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Disk getDisk() {
        return disk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeValue nodeValue = (NodeValue) o;
        return coordinate.equals(nodeValue.coordinate) && disk.equals(nodeValue.disk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, disk);
    }

    @Override
    public String toString() {
        return "NodeValue{" +
                "coordinate=" + coordinate +
                ", disk=" + disk +
                '}';
    }
}
