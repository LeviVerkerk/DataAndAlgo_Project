package org.leviverkerk;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            System.out.println(System.getProperty("user.dir"));
            Cliff input1Cliff = InputParser.readInput("./src/org/leviverkerk/testInput1.txt");
            System.out.println(input1Cliff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
