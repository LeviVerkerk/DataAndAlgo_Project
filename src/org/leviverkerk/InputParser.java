package org.leviverkerk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputParser {

    //TODO make sure inputhandler works with domjudge input
    // example for java:
    /*

    import java.util.*;
    class Main {
      public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.err.println("give me N"); // debug output
        int N = s.nextInt(); // read N from the input
        System.out.println(N); // write N to the output
      }
    }
     */

    public static Cliff readInput(String filePath) throws FileNotFoundException {
        //  Open file reader
        File file = new File(filePath);
        Scanner myReader = new Scanner(file);

        //  Read the first line and extract N, M and W
        String firstLine = myReader.nextLine();
        String[] nAndM = firstLine.split(" ");
        int N = Integer.parseInt(nAndM[0]);
        int M = Integer.parseInt(nAndM[1]);
        int W = Integer.parseInt(nAndM[2]);

        ArrayList<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String line = myReader.nextLine();
            String[] coordinatesString = line.split(" ");
            int x = Integer.parseInt(coordinatesString[0]);
            int y = Integer.parseInt(coordinatesString[1]);
            coordinates.add(new Coordinate(x, y));
        }

        ArrayList<Disk> disks = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            String line = myReader.nextLine();
            String[] coordinatesString = line.split(" ");
            int radius = Integer.parseInt(coordinatesString[0]);
            int cost = Integer.parseInt(coordinatesString[1]);
            disks.add(new Disk(cost, radius));
        }

        myReader.close();

        return new Cliff(N, M, W, coordinates, disks);
    }
}
