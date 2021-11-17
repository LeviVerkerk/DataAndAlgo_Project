package org.leviverkerk;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            System.out.println(System.getProperty("user.dir"));
            Cliff input1Cliff = InputParser.readInput("./src/org/leviverkerk/testInput1.txt");

            System.out.println(input1Cliff);

            System.out.println("--------NEIGHBOURS FROM (8,7) -----------");

            Node eightPointSeven = new Node(new Coordinate(8,7));
            eightPointSeven.setDistance(102);
            eightPointSeven.setCurrentDisk(new Disk(100, 3));

            System.out.println(findAllNeighbours(eightPointSeven, input1Cliff));

            System.out.println("=====================================");
            System.out.println("Start Dijkstra's");
            System.out.println("=====================================");

            System.out.println("Minimal cost is:" + dijkstra(input1Cliff));
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

        Map<Node, Integer> startingPoints = findStartPoints(cliff);
        int minCost = Integer.MAX_VALUE;
        Set<Node> visited = new HashSet<>();
        Map<Node, Integer> path = new HashMap<>();
        Queue<Node> PQueue = new PriorityQueue<>();

        for (Node V : cliff.getCoordinates()) {
            path.put(V, Integer.MAX_VALUE);
        }
        System.out.println("Starting points : ");
        System.out.println(startingPoints);
        for (Node V : startingPoints.keySet()) {
//            visited.add(V);
            V.setDistance(startingPoints.get(V));
            PQueue.add(V);
            path.put(V, startingPoints.get(V));
        }

        while (!PQueue.isEmpty()) {

            System.out.println("=====================");
            System.out.println("Current queue:" + PQueue);
            System.out.println("=====================");

            Node U = PQueue.poll();

            System.out.println("======================");
            System.out.println("Now visiting: " + U);
            System.out.println("======================");

            visited.add(U);

            Map<Node, Integer> neighbours = findAllNeighbours(U, cliff);

            for (Node V : neighbours.keySet()) {
                if (!visited.contains(V)) {
//                    System.out.println("tempDist = " + path.get(U) + " + " + neighbours.get(V));
                    int tempDist = path.get(U) + neighbours.get(V);
                    if (tempDist < path.get(V)) {
                        path.put(V, tempDist);
                        V.setDistance(tempDist);
                    }
                    System.out.println("Adding to the queue : " + V);

                    if (!PQueue.contains(V))
                        PQueue.add(V);
                    System.out.println("From" + U + "\nDiscovered " + V);
                }
            }
            visited.add(U);
        }

        System.out.println("------ PATH MAP ------");
        System.out.println(path);


        Map<Node, Integer> endPoints = findEndPoints(cliff);
        for (Node endPoint : endPoints.keySet()) {
            for (Node V : path.keySet()) {
                if (endPoint.equals(V)) {
                    if (endPoint.getRadius() >= V.getRadius()) {
                        int tempCost = path.get(V) - V.getCost() + endPoint.getCost();
                        if (tempCost < minCost) {
                            minCost = tempCost;
                        }
                    } else {
                        int tempCost = path.get(V) - endPoint.getCost() + V.getCost();
                        if (tempCost < minCost) {
                            minCost = tempCost;
                        }
                    }
                }
            }
        }

        return minCost;
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
                    if (neighbours.get(V) == null || neighbours.get(V) > Math.max(disk1.getCost(), disk2.getCost())) {
                        neighbours.put(V, disk2.getCost());
                        //  Assign smallest disk to source node
//                        if (disk1.getCost() <= disk2.getCost()) {
                        source.setCurrentDisk(disk1);
                        V.setCurrentDisk(disk2);
//                        } else {
//                            source.setCurrentDisk(disk2);
//                            V.setCurrentDisk(disk1);
//                        }
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

