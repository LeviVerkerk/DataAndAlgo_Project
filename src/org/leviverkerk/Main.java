package org.leviverkerk;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            System.err.println(System.getProperty("user.dir"));
            Cliff input1Cliff = InputParser.readInput("./src/org/leviverkerk/testInput2.txt");

            System.err.println(input1Cliff);

            System.err.println("--------NEIGHBOURS FROM (15,4) -----------");

            Node fifteenPointFour = new Node(new Coordinate(15, 4));
            fifteenPointFour.setDistance(104);
            fifteenPointFour.setCurrentDisk(new Disk(1, 2));

            System.err.println(findAllNeighbours(fifteenPointFour, input1Cliff));

            System.err.println("=====================================");
            System.err.println("Start Dijkstra's");
            System.err.println("=====================================");

            int result = dijkstra(InputParser.readInput());

            if (result != Integer.MAX_VALUE) {
                System.out.println(result);
            } else {
                System.out.println("Impossible");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int dijkstra(Cliff cliff) {

        Map<Node, Integer> startingPoints = findStartPoints(cliff);
        int minCost = Integer.MAX_VALUE;
        Set<Node> visited = new HashSet<>();
        Map<Node, Integer> path = new HashMap<>();
        Queue<Node> PQueue = new PriorityQueue<>(cliff.getN(), Comparator.comparingInt(Node::getDistance));

        for (Node V : cliff.getCoordinates()) {
            path.put(V, Integer.MAX_VALUE);
        }
        System.err.println("Starting points : ");
        System.err.println(startingPoints);
        for (Node V : startingPoints.keySet()) {
            V.setDistance(startingPoints.get(V));
            PQueue.add(V);
            path.put(V, startingPoints.get(V));
        }

        while (!PQueue.isEmpty()) {
            Node U = PQueue.poll();

            System.err.println("======================");
            System.err.println("Now visiting: " + U);
            System.err.println("======================");

            visited.add(U);

            Map<Node, Integer> neighbours = findAllNeighbours(U, cliff);
            for (Node V : neighbours.keySet()) {
                if (!visited.contains(V)) {
                    int tempDist = path.get(U) + neighbours.get(V);
                    if (tempDist < path.get(V)) {
                        V.setCurrentDisk(cliff.getDisk(neighbours.get(V)));
                        V.setDistance(tempDist);
                        path.put(V, tempDist);
                    }

                    PQueue.remove(V);
                    PQueue.offer(V);
                    System.err.println("From" + U + "\nDiscovered " + V);
                }
            }
            visited.add(U);
        }

        System.err.println("------ PATH MAP ------");
        System.err.println(path);


        Map<Node, Integer> endPoints = findEndPoints(cliff);
        for (Node endPoint : endPoints.keySet()) {
            if (endPoint.getCurrentDisk() != null) {
                if (endPoint.getCost() >= endPoints.get(endPoint)) {
                    if (endPoint.getDistance() < minCost) {
                        minCost = endPoint.getDistance();
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
                    if (disk1.getRadius() <= source.getRadius()) {
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
                int sourceRadius = Math.max(disk1.getRadius(), source.getRadius());

                int radius = sourceRadius + disk2.getRadius();

                //  Check if node is in range with these disks
                if (radius >= distance(source.getCoordinate(), V.getCoordinate())) {
                    neighbours.computeIfAbsent(V, k -> disk2.getCost());
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
                        endPoints.put(V, disk.getCost());
                    }
                }
            }
        }
        return endPoints;
    }
}

