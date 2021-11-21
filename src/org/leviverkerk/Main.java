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
                graph.addNode(new Node( new NodeValue(coordinate, disk)));
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
            if(endPoint.getDistance() < minimalCost && endPoint.getDistance() >= 0){
                minimalCost = endPoint.getDistance();
            }
        }

        return minimalCost;

    }

    static Graph dijkstra(Graph graph, Set<Node> startingNodes) {
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>(startingNodes);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                //  ! Optimization: if source is the same as destination we can skip!
                if (currentNode != adjacentNode) {
                    Integer edgeWeight = adjacencyPair.getValue();
                    if (!settledNodes.contains(adjacentNode)) {
                        calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                        unsettledNodes.add(adjacentNode);
                    }
                }
            }
            settledNodes.add(currentNode);
        }

        return graph;
    }

    static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance <= lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeight);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    static Map<Node, Integer> findStartPoints(Graph graph) {
        Map<Node, Integer> startPoints = new HashMap<>();

        graph.getNodes().stream().filter(node -> node.getValue().getCoordinate().getY() <= node.getValue().getDisk().getRadius())
                .forEach(node -> startPoints.put(node, node.getValue().getDisk().getCost()));

        return startPoints;
    }

    static Set<Node> findEndPoints(Graph graph, int W) {
        Set<Node> endPoints = new HashSet<>();
        graph.getNodes().stream().filter(node -> {
            int yOfNode = node.getValue().getCoordinate().getY();
            int radiusOfNode = node.getValue().getDisk().getRadius();

            return yOfNode + radiusOfNode >= W;
        }).forEach(endPoints::add);

        return endPoints;
    }

    static void addDestinations (Graph graph, Node source) {
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

    static double distance(Node a, Node b) {
        final Coordinate A = a.getValue().getCoordinate();
        final Coordinate B = b.getValue().getCoordinate();

        return Math.hypot(Math.abs(A.getX() - B.getX()), Math.abs(A.getY() - B.getY()));
    }
}

