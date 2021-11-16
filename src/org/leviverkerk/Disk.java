package org.leviverkerk;

import java.util.Comparator;

public class Disk implements Comparator<Disk>, Comparable<Disk> {

    private int cost;
    private int radius;

    public Disk(int cost, int radius) {
        this.cost = cost;
        this.radius = radius;
    }

    public int getCost() {
        return cost;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public int compareTo(Disk o) {
        return Integer.compare(cost, o.cost);
    }

    @Override
    public int compare(Disk o1, Disk o2) {
        return Integer.compare(o1.cost, o2.cost);
    }

    @Override
    public String toString() {
        return "Disk{" +
                "cost=" + cost +
                ", radius=" + radius +
                "} \n";
    }
}
