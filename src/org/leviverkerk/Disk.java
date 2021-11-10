package org.leviverkerk;

public class Disk {

    private int cost;
    private int radius;

    public Disk(int cost, int radius) {
        this.cost = cost;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "cost=" + cost +
                ", radius=" + radius +
                "} \n";
    }
}