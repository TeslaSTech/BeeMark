package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static int timesToRun = 1000; // Times to run each benchmark (total iterations = 2x this)
    // Best to keep this to round numbers or otherwise the progress bar may not be perfectly functional

    public static void main(String[] args) {
        long startTime, endTime;
        Scanner in = new Scanner(System.in);
        long ScannerAVG[] = new long[timesToRun];
        long BWAVG[] = new long[timesToRun];
        long ScannerTotal = 0, BWTotal = 0, BWAVGI = 0, ScannerAVGI = 0;

        System.out.println("Benchmark Progress:\n0%            [Scanner]  ||  [BufferedReader]   100%");
        System.out.print("[");
        for (int i = 0; i < timesToRun; i++) {
            startTime = System.nanoTime();
            ScannerRead();
            endTime = System.nanoTime();
            ScannerAVG[i] = (endTime-startTime)/1000;
            ScannerTotal = ScannerTotal + ScannerAVG[i];
            if (i % (timesToRun/25) == 0)
                System.out.print("=");
            System.gc(); // FINALLY DOESN'T USE 6.5GB OF MEMORY
        }
        for (int i = 0; i < timesToRun; i++) {
            startTime = System.nanoTime();
            BufferedRead();
            endTime = System.nanoTime();
            BWAVG[i] = (endTime-startTime)/1000;
            BWTotal = BWTotal + BWAVG[i];
            if (i % (timesToRun/25) == 0)
                System.out.print("=");
            System.gc();
        }
        System.out.println("]\n\n");

        BWAVGI = BWTotal / timesToRun;
        ScannerAVGI = ScannerTotal / timesToRun;

        System.out.println("Scanner average time to run with " + timesToRun + " iterations (µs):" + ScannerAVGI);
        System.out.println("BufferedReader average time to run with " + timesToRun + " iterations (µs):" + BWAVGI);
        long score = 10000 - (ScannerAVGI+BWAVGI)/2;
        System.out.println("Score (higher is better): " + score);
    }

    // Methods
    public static void ScannerRead() {
        try {
            Scanner read = new Scanner(new File("src/com/company/in.txt"));
            while (read.hasNextLine()) {
                read.nextLine();
            }
        } catch (FileNotFoundException fne) {
            System.out.println(fne.toString());
        }
    }

    public static void BufferedRead() {
        String readS = "";
        try {
            BufferedReader read = new BufferedReader(new FileReader("src/com/company/in.txt"));
            do {
                readS = read.readLine();
            } while (readS != null);
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }

}
