package org.leviverkerk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputParser {

    public static Cliff readInput() throws FileNotFoundException {
        //  Open file reader
        Scanner myReader = new Scanner(System.in);

        //  Read the first line and extract N, M and W
        return getCliff(myReader);
    }

    private static Cliff getCliff(Scanner myReader) {
        String firstLine = myReader.nextLine();
        String[] nAndM = firstLine.split(" ");
        int N = Integer.parseInt(nAndM[0]);
        int M = Integer.parseInt(nAndM[1]);
        int W = Integer.parseInt(nAndM[2]);

        ArrayList<Node> nodes = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String line = myReader.nextLine();
            String[] coordinatesString = line.split(" ");
            int x = Integer.parseInt(coordinatesString[0]);
            int y = Integer.parseInt(coordinatesString[1]);
            nodes.add(new Node(new Coordinate(x, y)));
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

        return new Cliff(N, M, W, nodes, disks);
    }

    public static Cliff readInput(String filePath) throws FileNotFoundException {
        //  Open file reader
        File file = new File(filePath);
        Scanner myReader = new Scanner(file);

        //  Read the first line and extract N, M and W
        return getCliff(myReader);
    }
}
