package org.leviverkerk;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(System.getProperty("user.dir"));
            Cliff input1Cliff = InputParser.readInput("./src/org/leviverkerk/testInput1.txt");

            System.out.println(input1Cliff);

            System.out.println("--------NEIGHBOURS FROM " + new Coordinate(8, 7) + "-----------");

            System.out.println(findAllNeighbours(new Node (new Coordinate(8, 7), 102, new Disk(100, 3)), input1Cliff));

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

        Map<Node, Integer> startingPoints = findStartPoints(cliff);

        int minCost = Integer.MAX_VALUE;

        for (Node source : startingPoints.keySet()) {

            System.out.println("---------------- FROM STARTING POINT " + source + "------------------");

            Set<Node> visited = new HashSet<>();

            Map<Coordinate, Integer> path = new HashMap<>();

            Queue<Node> PQueue = new PriorityQueue<>();

            //  Set all distances to infinity
            for (Coordinate V : cliff.getCoordinates()) {
                path.put(V, Integer.MAX_VALUE);
//                if (!V.equals(source)) {
//                    PQueue.add(new Node(V, Integer.MAX_VALUE));
//                }
            }

            //  Set the cost for source node to 0
            path.put(source.getCoordinate(), startingPoints.get(source));
            PQueue.add(source);

            //  Loop through the queue until it's empty
            while (!PQueue.isEmpty()) {
                //  Retrieve the minimal element U from the queue
                Node U = PQueue.remove();
                System.out.println("NOW VISITING: " + U);
                //  Retrieve a map of all reachable neighbours from U, associated with the minimal cost it takes to get there
                Map<Node, Integer> neighbours = findAllNeighbours(U, cliff);
                for (Node V : neighbours.keySet()) {
                    if (!visited.contains(V)) { //check if destination is NOT one of  starting nodes
                        //  For each neighbour we set it to the cost it takes to get to node U + the cost from U to V
                        int tempDist = path.get(U.getCoordinate()) + neighbours.get(V);
                        //TODO: optimalization: check if destination != source, continue if it is

                        //TODO: important optimalization for later
                        //TODO: check if current length is bigger then current path to end
                        //TODO: if this is the case continue, before replacing or adding to queue

                        //  If this cost is less than the one previously assigned we replace it.
                        if (tempDist < path.get(V.getCoordinate())) {
                            path.put(V.getCoordinate(), tempDist);
                        }

                        PQueue.add(new Node(V.getCoordinate(), tempDist, V.getCurrentDisk()));

                        System.out.println("From" + U + "Discovered" + V + ", " + tempDist);
                    }
                }
                visited.add(U);
            }

//            for (Coordinate vertex : path.keySet()) {
//                System.out.println("Vertex:" + vertex + ", " + path.get(vertex));
//            }

            /*
            Finally, we find a map of all endPoints. These are points from which we're able to reach the end.
            They are mapped to the associated cost it takes to get from the endPoint to the end
            */
            int totalCost = Integer.MAX_VALUE;
            Map<Coordinate, Integer> endPoints = findEndPoints(cliff);
            for (Coordinate endPoint : endPoints.keySet()) {
                int costFromEndPointToCliff = endPoints.get(endPoint);
                int costFromSourceToEndPoint = path.get(endPoint);
                int costFromCliffToSource = startingPoints.get(source);

                if (costFromSourceToEndPoint == Integer.MAX_VALUE) {
                    totalCost = Integer.MAX_VALUE;
                } else {
                    totalCost = costFromCliffToSource + costFromEndPointToCliff + costFromSourceToEndPoint;
                }

                if (totalCost < minCost) {
                    minCost = totalCost;
                }

            }

            System.out.println("minCost from " + source + totalCost);

        }

        return minCost;
    }

    /*
    @param Coordinate source, this is the source node from where we want to find the neighbours.
    @param Cliff cliff, this is the cliff for which we have to solve.

    @return Map<Coordinate, Integer> where each coordinate, which is a neighbour of source, is mapped to the cost it takes to get from source to this coordinate
     */
    static Map<Node, Integer> findAllNeighbours(Node source, Cliff cliff) {

        Map<Node, Integer> neighbours = new HashMap<>();

        for (Disk disk : cliff.getDisks()) {
            for (Disk disk1 : cliff.getDisks()) {
                int radius = disk.getRadius() + disk1.getRadius();
                int cost = disk.getCost() + disk1.getCost();

                Disk sourceDisk;
                Disk destDisk;

                if (disk.getCost() < disk1.getCost() && (source.getCurrentDisk().getCost() <= disk.getCost())) {
                    sourceDisk = disk;
                    destDisk = disk1;
                } else {
                    sourceDisk = disk1;
                    destDisk = disk;
                }

                for (Coordinate coordinate : cliff.getCoordinates()) {

                    double dist = distance(source.getCoordinate(), coordinate);

                    if (dist <= radius && dist != 0) {
                        Node newNode = new Node(coordinate, destDisk.getCost(), destDisk);
                        if (neighbours.get(newNode) != null) {
                            if (neighbours.get(newNode) > destDisk.getCost()) {
                                neighbours.put(newNode, destDisk.getCost());
                            }
                        } else {
                            neighbours.put(newNode, destDisk.getCost());
                        }
                    }
                }
            }
        }

        return neighbours;
    }

    // TODO: save which disk is used on destination in list of edges (radius AND cost), save this when traversing in dijkstra
    // TODO: when calculating new cost, substract the cost of this disk when iterating over first layer of disks
    // TODO: So new_cost = cost_so_far + costdisk1 - disk_cost_already_there + costdisk2

    static double distance(Coordinate a, Coordinate b) {
        return Math.hypot(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    }

    static Map<Node, Integer> findStartPoints(Cliff cliff) {
        Map<Node, Integer> start = new HashMap<>();

        for (Disk disk : cliff.getDisks()) {
            int radius = disk.getRadius();
            int cost = disk.getCost();

            for (Coordinate coordinate : cliff.getCoordinates()) {
                double dist = coordinate.getY();

                if (dist <= radius && dist != 0) {
                    Node newNode = new Node(coordinate, cost, disk);
                    if (start.get(newNode) != null) {
                        if (start.get(newNode) > cost) {
                            start.put(newNode, cost);
                        }
                    } else {
                        start.put(newNode, cost);
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
                double dist = cliff.getW() - coordinate.getY();

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

