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

            System.out.println("--------NEIGHBOURS FROM " + input1Cliff.getCoordinates().get(0) + "-----------");

            System.out.println(findAllNeighbours(input1Cliff.getCoordinates().get(0), input1Cliff));

            System.out.println("All starting points:");

            System.out.println(findStartPoints(input1Cliff));

            System.out.println("All end points:");

            System.out.println(findEndPoints(input1Cliff));

            System.out.println(dijkstra(input1Cliff));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int dijkstra(Cliff cliff) {

        Map<Coordinate, Integer> startingPoints = findStartPoints(cliff);

        int minCost = Integer.MAX_VALUE;

        for (Coordinate source : startingPoints.keySet()) {

            Map<Coordinate, Integer> path = new HashMap<>();
//            Map<Coordinate, Coordinate> previous = new HashMap<>();

            Queue<Node> PQueue = new PriorityQueue<>();

            for (Coordinate V : cliff.getCoordinates()) {
                path.put(V, Integer.MAX_VALUE);
                if (!V.equals(source)) {
                    PQueue.add(new Node(V, Integer.MAX_VALUE));
                }
            }

            path.put(source, 0);
            PQueue.add(new Node(source, 0));

            while (!PQueue.isEmpty()) {
                Coordinate U = PQueue.remove().getCoordinate();
                Map<Coordinate, Integer> neighbours = findAllNeighbours(U, cliff);
                for (Coordinate V : neighbours.keySet()) {
                    int tempDist = path.get(U) + neighbours.get(V);
                    if (tempDist < path.get(V)) {
                        path.put(V, tempDist);
                    }
                }
            }

            Map<Coordinate, Integer> endPoints = findEndPoints(cliff);
            for (Coordinate endPoint : endPoints.keySet()) {
                int costFromEndPointToCliff = endPoints.get(endPoint);
                int costFromSourceToEndPoint = path.get(endPoint);
                int costFromCliffToSource = startingPoints.get(source);

                int totalCost = costFromCliffToSource + costFromEndPointToCliff + costFromSourceToEndPoint;

                if (totalCost < minCost) {
                    minCost = totalCost;
                }

            }

        }

        return minCost;
    }

    static Map<Coordinate, Integer> findAllNeighbours(Coordinate source, Cliff cliff) {

        Map<Coordinate, Integer> neighbours = new HashMap<>();

        for (Disk disk : cliff.getDisks()) {
            for (Disk disk1 : cliff.getDisks()) {
                int radius = disk.getRadius() + disk1.getRadius();
                int cost = disk.getCost() + disk1.getCost();

                for (Coordinate coordinate : cliff.getCoordinates()) {

                    double dist = distance(source, coordinate);

                    if (dist <= radius && dist != 0) {
                        if (neighbours.get(coordinate) != null) {
                            if (neighbours.get(coordinate) > cost) {
                                neighbours.put(coordinate, cost);
                            }
                        } else {
                            neighbours.put(coordinate, cost);
                        }
                    }
                }
            }
        }

        return neighbours;
    }

    static double distance(Coordinate a, Coordinate b) {
        return Math.hypot(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    }

    static Map<Coordinate, Integer> findStartPoints(Cliff cliff) {
        Map<Coordinate, Integer> start = new HashMap<>();

        for (Disk disk : cliff.getDisks()) {
            int radius = disk.getRadius();
            int cost = disk.getCost();

            for (Coordinate coordinate : cliff.getCoordinates()) {
                double dist = coordinate.getY();

                if (dist <= radius && dist != 0) {
                    if (start.get(coordinate) != null) {
                        if (start.get(coordinate) > cost) {
                            start.put(coordinate, cost);
                        }
                    } else {
                        start.put(coordinate, cost);
                    }
                }
            }
        }
        return start;
    }

    static Map<Coordinate, Integer> findEndPoints(Cliff cliff) {
        Map<Coordinate, Integer> end = new HashMap<>();

        for (Disk disk : cliff.getDisks()) {
            int radius = disk.getRadius();
            int cost = disk.getCost();

            for (Coordinate coordinate : cliff.getCoordinates()) {
                double dist = cliff.getW()-coordinate.getY();

                if (dist <= radius && dist != 0) {
                    if (end.get(coordinate) != null) {
                        if (end.get(coordinate) > cost) {
                            end.put(coordinate, cost);
                        }
                    } else {
                        end.put(coordinate, cost);
                    }
                }
            }
        }
        return end;
    }

    void runAlgorithm(Cliff cliff) {

    }


}

