package com.mdupas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static final String input_url = "G:/Prog/Advent_of_code_2022/inputs/day2/input.txt";

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input_url));
            secondPuzzle(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found at " + input_url);
        } catch (IOException e) {
            System.err.println("I/O error : ");
            e.printStackTrace();
        }
    }

    private static void firstPuzzle(BufferedReader reader) throws IOException {
        int totalScore = 0;
        while (reader.ready()) {
            String line = reader.readLine();
            Move moveElf = Move.generateMove(line.charAt(0));
            Move movePlayer = Move.generateMove(line.charAt(2));
            totalScore += movePlayer.moveValue;
            totalScore += Move.evaluateMove(movePlayer, moveElf);
        }
        System.out.println("Total result = " + totalScore);
    }

    private static void secondPuzzle(BufferedReader reader) throws IOException {
        int totalScore = 0;
        while (reader.ready()) {
            String line = reader.readLine();
            Move moveElf = Move.generateMove(line.charAt(0));
            Move movePlayer = Move.generateMoveOnResult(line.charAt(2), moveElf);
            totalScore += movePlayer.moveValue;
            totalScore += Move.evaluateMove(movePlayer, moveElf);
        }
        System.out.println("Total result = " + totalScore);
    }
}
