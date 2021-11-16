package org.leviverkerk;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            System.out.println(System.getProperty("user.dir"));
            Cliff input1Cliff = InputParser.readInput("./src/org/leviverkerk/testInput1.txt");

            System.out.println(input1Cliff);

            System.out.println("--------NEIGHBOURS FROM " + input1Cliff.getCoordinates().get(1) + "-----------");

            System.out.println(findAllNeighbours(input1Cliff.getCoordinates().get(1), input1Cliff));
//
//            System.out.println("All starting points:");
//
//            System.out.println(findStartPoints(input1Cliff));
//
//            System.out.println("All end points:");
//
//            System.out.println(findEndPoints(input1Cliff));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int dijkstra(Cliff cliff) {

        int[] distances = new int[cliff.getN()];
        Disk[] disks = new Disk[cliff.getN()];
        Node[] parents = new Node[cliff.getN()];

        for (int i = 0; i < cliff.getN(); i++) {
            distances[i] = Integer.MAX_VALUE;
            disks[i] = null;
            parents[i] = null;
        }

        distances[0] = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>();


        return -1;
    }

    static Map<Node, Integer> findAllNeighbours(Node source, Cliff cliff) {

        Map<Node, Integer> neighbours = new HashMap<>();

        if (source.getCurrentDisk() != null) {
            for (Disk disk1 : cliff.getDisks()) {
                for (Disk disk2 : cliff.getDisks()) {
                    if (disk1.getRadius() >= source.getRadius()) {
                        helper(source, cliff, neighbours, disk1, disk2);
                    }
                }
            }
        } else {
            for (Disk disk1 : cliff.getDisks()) {
                for (Disk disk2 : cliff.getDisks()) {
                    helper(source, cliff, neighbours, disk1, disk2);
                }
            }
        }

        neighbours.remove(source);
        return neighbours;
}

    private static void helper(Node source, Cliff cliff, Map<Node, Integer> neighbours, Disk disk1, Disk disk2) {
        for (Node V : cliff.getCoordinates()) {
            if (!V.equals(source)) {
                int radius = disk1.getRadius() + disk2.getRadius();
                int cost = disk1.getCost() + disk2.getCost();

                //  Check if node is in range with these disks
                if (radius >= distance(source.getCoordinate(), V.getCoordinate())) {
                    // If this node isn't saved yet OR the minimal cost was larger write it to map
                    if (neighbours.get(V) == null || neighbours.get(V) > cost) {
                        neighbours.put(V, cost);
                        //  Assign smallest disk to source node
                        if (disk1.getCost() <= disk2.getCost()) {
                            source.setCurrentDisk(disk1);
                            V.setCurrentDisk(disk2);
                        } else {
                            source.setCurrentDisk(disk2);
                            source.setCurrentDisk(disk1);
                        }
                    }
                }
            }
        }
    }

    static double distance(Coordinate a, Coordinate b) {
        return Math.hypot(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    }

    static Map<Node, Integer> findStartPoints(Cliff cliff) {
        Map<Node, Integer> startPoints = new HashMap<>();

        for (Node V : cliff.getCoordinates()) {
            for (Disk disk : cliff.getDisks()) {
                if (disk.getRadius() >= V.getCoordinate().getY()) {
                    if (startPoints.get(V) == null || startPoints.get(V) > disk.getCost()) {
                        V.setCurrentDisk(disk);
                        startPoints.put(V, disk.getCost());
                    }
                }
            }
        }

        return startPoints;
    }

    static Map<Node, Integer> findEndPoints(Cliff cliff) {
        Map<Node, Integer> endPoints = new HashMap<>();

        for (Node V : cliff.getCoordinates()) {
            for (Disk disk : cliff.getDisks()) {
                if (cliff.getW() - disk.getRadius() <= V.getCoordinate().getY()) {
                    if (endPoints.get(V) == null || endPoints.get(V) > disk.getCost()) {
                        V.setCurrentDisk(disk);
                        endPoints.put(V, disk.getCost());
                    }
                }
            }
        }
        return endPoints;
    }
}

