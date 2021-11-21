package org.leviverkerk;

import java.util.Scanner;

public class Addition {

    public static void main(String[] args) {
        Scanner myReader = new Scanner(System.in);

        int sum = 0;
        for (int i = Integer.parseInt(myReader.nextLine()); i > 0; i--) {
            sum += myReader.nextInt();
        }
        System.out.println(sum);
    }
}
