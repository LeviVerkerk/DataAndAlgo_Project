package org.leviverkerk;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int dijkstra(Cliff cliff) {

        Map<Coordinate, Integer> startingPoints = findStartPoints(cliff);

        for (Coordinate source : startingPoints.keySet()) {
            Map<Coordinate, Integer> dist = new HashMap<>();
            for (int i = 0; i < cliff.getCoordinates().size(); i++) {
                dist.put(cliff.getCoordinates().get(i), Integer.MAX_VALUE);
            }
            dist.put(source, startingPoints.get(source));

            

        }
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

