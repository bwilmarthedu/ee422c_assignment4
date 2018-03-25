package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Brian Wilmarth
 * bw24274
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

import java.lang.reflect.Array;

import java.text.ParseException;
import java.util.Scanner;
import java.io.*;

import java.lang.reflect.Method;
import java.util.List;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // TODO Write code here
        // Test Code

        // Actual Code
        String input = new String();
        String[] tokens = new String[3];

        input = kb.nextLine();
        tokens = input.split("\\s+");

        while(!tokens[0].equals("quit")){
            switch(tokens[0]){

                case "show":
                    try {
                        if (tokens.length > 1) { throw new Exception(); }
                        Critter.displayWorld();
                    }
                    catch(Exception e){
                        errorProcessing(input);
                    }
                    break;

                case "step":
                    try {
                        if (tokens.length > 1) {
                            if(tokens.length > 2){ throw new Exception(); }
                            for (int i = 0; i < Integer.parseInt(tokens[1]); i++) {
                                Critter.worldTimeStep();
                            }
                        } else {
                            if(tokens.length > 1){ throw new Exception(); }
                            Critter.worldTimeStep();
                        }
                    }
                    catch(Exception e){
                        errorProcessing(input);
                    }
                    break;

                case "seed":
                    try {
                        if(tokens.length > 2){ throw new Exception(); }
                        Critter.setSeed(Integer.parseInt(tokens[1]));
                    }
                    catch(Exception e){
                        errorProcessing(input);
                    }
                    break;

                case "make":
                    int count;
                    if(tokens.length == 3) {
                        try {
                            if(tokens.length > 3){ throw new Exception(); }
                            count = Integer.parseInt(tokens[2]);
                            for (int i = 0; i < count; i++) {
                                Critter.makeCritter(tokens[1]);
                            }
                        } catch (Exception e) {
                            errorProcessing(input);
                        }
                    }
                    else{
                        try{
                            if(tokens.length > 2){ throw new Exception(); }
                            Critter.makeCritter(tokens[1]);
                        }
                        catch (Exception e){
                            errorProcessing(input);
                        }
                    }
                    break;

                case "stats":
                    try {
                        if(tokens.length > 2){ throw new Exception(); }
                        Class<Critter> c = (Class<Critter>) Class.forName(myPackage + "." + tokens[1]);
                        Method method = c.getMethod("runStats", List.class);
                        method.invoke(c, Critter.getInstances(tokens[1]));
                    }
                    catch (Exception e){
                        errorProcessing(input);
                    }
                    break;
                default:
                    errorProcessing(input);
            }
            input = kb.nextLine();
            tokens = input.split("\\s+");
        }

        /* Write your code above */
        System.out.flush();

    }

    /**
     * Prints out an error processing message
     * @param badCommand the command to print
     */
    private static void errorProcessing(String badCommand){
        System.out.println("error processing: " + badCommand);
    }
}
