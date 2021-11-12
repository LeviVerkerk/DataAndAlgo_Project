package org.leviverkerk;

import java.util.ArrayList;

public class Cliff {

    private int N;
    private int M;
    private int W;

    private ArrayList<Coordinate> coordinates;
    private ArrayList<Disk> disks;

    public Cliff(int n, int m, int w, ArrayList<Coordinate> coordinates, ArrayList<Disk> disks) {
        N = n;
        M = m;
        W = w;
        this.coordinates = coordinates;
        this.disks = disks;
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

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public ArrayList<Disk> getDisks() {
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
