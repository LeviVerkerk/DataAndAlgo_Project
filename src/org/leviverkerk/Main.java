package org.leviverkerk;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
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

            Queue<Node> PQueue = new PriorityQueue<>();

            //  Set all distances to infinity
            for (Coordinate V : cliff.getCoordinates()) {
                path.put(V, Integer.MAX_VALUE);
                if (!V.equals(source)) {
                    PQueue.add(new Node(V, Integer.MAX_VALUE));
                }
            }

            //  Set the cost for source node to 0
            path.put(source, 0);
            PQueue.add(new Node(source, 0));

            //  Loop through the queue until it's empty
            while (!PQueue.isEmpty()) {
                //  Retrieve the minimal element U from the queue
                Coordinate U = PQueue.remove().getCoordinate();
                //  Retrieve a map of all reachable neighbours from U, associated with the minimal cost it takes to get there
                Map<Coordinate, Integer> neighbours = findAllNeighbours(U, cliff);
                for (Coordinate V : neighbours.keySet()) {
                    //  For each neighbour we set it to the cost it takes to get to node U + the cost from U to V
                    int tempDist = path.get(U) + neighbours.get(V);
                    //  If this cost is less than the one previously assigned we replace it.
                    if (tempDist < path.get(V)) {
                        path.put(V, tempDist);
                    }
                }
            }

            /*
            Finally, we find a map of all endPoints. These are points from which we're able to reach the end.
            They are mapped to the associated cost it takes to get from the endPoint to the end
            */
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

    /*
    @param Coordinate source, this is the source node from where we want to find the neighbours.
    @param Cliff cliff, this is the cliff for which we have to solve.

    @return Map<Coordinate, Integer> where each coordinate, which is a neighbour of source, is mapped to the cost it takes to get from source to this coordinate
     */
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
}

