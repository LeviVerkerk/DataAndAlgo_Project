package org.leviverkerk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cliff {

    private int N;
    private int M;
    private int W;

    private List<Node> coordinates;
    private List<Disk> disks;

    public Cliff(int n, int m, int w, ArrayList<Node> coordinates, ArrayList<Disk> disks) {
        N = n;
        M = m;
        W = w;
        this.coordinates = coordinates;
        this.disks = disks.stream().sorted().collect(Collectors.toList());
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public int getW() {
        return W;
    }

    public List<Node> getCoordinates() {
        return coordinates;
    }

    public List<Disk> getDisks() {
        return disks;
    }

    @Override
    public String toString() {
        return "Cliff{" +
                "N=" + N +
                "\n M=" + M +
                "\n W=" + W +
                "\n coordinates=" + coordinates +
                "\n disks=" + disks +
                '}';
    }
}
