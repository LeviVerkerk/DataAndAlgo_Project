package org.leviverkerk;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            System.err.println("=====================================");
            System.err.println("Start Dijkstra's");
            System.err.println("=====================================");

            int result = getMinimalCost(InputParser.readInput("./src/org/leviverkerk/testInput1.txt"));

            if (result != Integer.MAX_VALUE && result >= 0) {
                System.out.println(result);
            } else {
                System.out.println("Impossible");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int getMinimalCost(Cliff cliff) {
        //  Create a Node foreach pilar in combination with disk
        Graph graph = new Graph();

        for (Coordinate coordinate : cliff.getCoordinates()) {
            for (Disk disk : cliff.getDisks()) {
                graph.addNode(new Node(new NodeValue(coordinate, disk)));
            }
        }

        // Add all destinations to all nodes (with their corresponding edge weight)
        graph.getNodes().forEach(source -> {
            addDestinations(graph, source);
        });

        // Set distances for each startingPoint
        Set<Node> startingNodes = new HashSet<>();
        findStartPoints(graph).forEach((key, value) -> {
            key.setDistance(value);
            startingNodes.add(key);
        });

        //  Call #dijksta
        System.err.println(dijkstra(graph, startingNodes));

        // Find all endPoints and get endPoint with minimal cost
        int minimalCost = Integer.MAX_VALUE;
        for (Node endPoint : findEndPoints(graph, cliff.getW())) {
            if (endPoint.getDistance() < minimalCost && endPoint.getDistance() >= 0) {
                minimalCost = endPoint.getDistance();
            }
        }

        return minimalCost;

    }

    /*
    Runtime complexity
     */
    static Graph dijkstra(Graph graph, Set<Node> startingNodes) {
        Set<Node> visited = new HashSet<>();
        Set<Node> unvisited = new HashSet<>(startingNodes);

        while (unvisited.size() != 0) {
            //  Runtime complexity O(n) with n
            Node currentNode = getClosestNode(unvisited);
            unvisited.remove(currentNode);
            for (Map.Entry<Node, Integer> neighbouringPair : currentNode.getNeighbouringNodes().entrySet()) {
                Node neighbourNode = neighbouringPair.getKey();
                //  ! Optimization: if source is the same as destination we can skip!
                if (currentNode != neighbourNode) {
                    Integer edgeWeight = neighbouringPair.getValue();
                    if (!visited.contains(neighbourNode)) {
                        setMinimumDistance(neighbourNode, edgeWeight, currentNode);
                        unvisited.add(neighbourNode);
                    }
                }
            }
            visited.add(currentNode);
        }

        return graph;
    }

    /*
    Runtime complexity of O(n) with n being the amount of unsettled nodes
     */
    static Node getClosestNode(Set<Node> unvisited) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unvisited) {
            int nodeDistance = node.getDistance();
            if (nodeDistance <= lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /*
    Sets
    Runtime complexity O(1)
     */
    static void setMinimumDistance(Node destinationNode, Integer edgeWeight, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight < destinationNode.getDistance()) {
            destinationNode.setDistance(sourceDistance + edgeWeight);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            destinationNode.setShortestPath(shortestPath);
        }
    }

    /*
    @return a map of all nodes which can reach the beginning mapped to their distance
    @graph this is the graph in which we want to look
    Runtime complexity O(n) with n being the amount of nodes in the graph
     */
    static Map<Node, Integer> findStartPoints(Graph graph) {
        Map<Node, Integer> startPoints = new HashMap<>();

        graph.getNodes().stream().filter(node -> node.getValue().getCoordinate().getY() <= node.getValue().getDisk().getRadius())
                .forEach(node -> startPoints.put(node, node.getValue().getDisk().getCost()));

        return startPoints;
    }

    /*
    @return a set with all nodes that reach the end (of the cliff)
    @graph this is the graph in which we want to look
    @W this is the height of the cliff
    Runtime complexity O(n) with n being the amount of nodes in the graph
     */
    static Set<Node> findEndPoints(Graph graph, int W) {
        Set<Node> endPoints = new HashSet<>();
        graph.getNodes().stream().filter(node -> {
            int yOfNode = node.getValue().getCoordinate().getY();
            int radiusOfNode = node.getValue().getDisk().getRadius();

            return yOfNode + radiusOfNode >= W;
        }).forEach(endPoints::add);

        return endPoints;
    }

    /*
    This method adds all neighbours as destination to a node and adds the edge weight to the transaction
    @graph this is the graph in which we want to search
    @source this is the node for which we want to find each neighbour
    Run time complexity O(n) with n being the amount of nodes in the graph
     */
    static void addDestinations(Graph graph, Node source) {
        int sourceRadius = source.getValue().getDisk().getRadius();

        for (Node destination : graph.getNodes()) {
            int destinationRadius = destination.getValue().getDisk().getRadius();
            int totalRadius = sourceRadius + destinationRadius;
            if (distance(source, destination) <= totalRadius) {
                int destinationCost = destination.getValue().getDisk().getCost();
                source.addDestination(destination, destinationCost);
            }
        }

    }

    /*
    @return the distance between node a and b
    Runtime complexity O(1)
     */
    static double distance(Node a, Node b) {
        final Coordinate A = a.getValue().getCoordinate();
        final Coordinate B = b.getValue().getCoordinate();

        return Math.hypot(Math.abs(A.getX() - B.getX()), Math.abs(A.getY() - B.getY()));
    }
}

