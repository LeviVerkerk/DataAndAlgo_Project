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

//            System.out.println(findAllNeighbours(input1Cliff.getCoordinates().get(0), input1Cliff));

            System.out.println("All starting points:");

            System.out.println(findStartPoints(input1Cliff));

            System.out.println("All end points:");

            System.out.println(findEndPoints(input1Cliff));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int dijkstra(Cliff cliff) {

    }

    static Map<Node, Integer> findAllNeighbours(Node source, Cliff cliff) {

    }

    static double distance(Coordinate a, Coordinate b) {
        return Math.hypot(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    }

    static Map<Coordinate, Integer> findStartPoints(Cliff cliff) {

    }

    static Map<Coordinate, Integer> findEndPoints(Cliff cliff) {

    }
}

