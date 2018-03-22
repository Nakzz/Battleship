/**
 * This file contains testing methods for the Battleship project. These methods are intended to 
 * provide an example of a way to incrementally test your code, and to provide example method calls
 * for the Battleship methods
 *
 * Toward these objectives, the expectation is that part of the grade for the Battleship project is 
 * to write some tests and write header comments summarizing the tests that have been written. 
 * Specific places are noted with FIXME but add any other comments you feel would be useful.
 */

import java.util.Random;
import java.util.Scanner;

/**
 * This class contains a few methods for testing methods in the Battleship
 * class as they are developed. These methods are all private as they are only
 * intended for use within this class.
 * 
 * @author Marc Renault
 * @author Ajmain Naqib
 *
 */
public class TestBattleship {

    public static char[][] boardDim = new char[6][8];
    /**
     * This is the main method that runs the various tests. Uncomment the tests when
     * you are ready for them to run.
     * 
     * @param args (unused)
     */
    public static void main(String[] args) {
        // InitBoard
        
        Battleship.initBoard(boardDim);
        
        // Milestone 1
        //testCoordAlphaToNum();
        //testCoordNumToAlpha();
        //testPromptInt();
        //testPromptStr();
        // Milestone 2
        testCheckWater();
        testPlaceShip();
        testPlaceRandomShip();
        // Milestone 3
        //testTakeShot();
        //testCheckLost();
    }
    
    private static void testCoordAlphaToNum() {
        int numTests = 6;
        int passed = numTests;
        int res;
        if((res = Battleship.coordAlphaToNum("BAAA")) != 17576) {
            System.out.println("FAILED: Battleship.coordAlphaToNum(\"BAAA\") != 17576, but " + res);
            passed--;
        }
        if((res = Battleship.coordAlphaToNum("ZERTY")) != 11506714) {
            System.out.println("FAILED: Battleship.coordAlphaToNum(\"ZERTY\") != 11506714, but " + res);
            passed--;
        }
        if((res = Battleship.coordAlphaToNum("zerty")) != 11506714) {
            System.out.println("FAILED: Battleship.coordAlphaToNum(\"zerty\") != 11506714, but " + res);
            passed--;
        }
        if((res = Battleship.coordAlphaToNum("&é\"")) != -14747) {
            System.out.println("FAILED: Battleship.coordAlphaToNum(\"&é\\\"\") != -14747, but " + res);
            passed--;
        }
        if((res = Battleship.coordAlphaToNum("baaa")) != 17576) {
            System.out.println("FAILED: Battleship.coordAlphaToNum(\"baaa\") != 17576, but " + res);
            passed--;
        }
        if((res = Battleship.coordAlphaToNum("&é\"")) != -14747) {
            System.out.println("FAILED: Battleship.coordAlphaToNum(\"&é\\\"\") != -14747, but " + res);
            passed--;
        }
        System.out.println("testCoordAlphatoNum: Passed " + passed + " of " + numTests + " tests.");
    }

    private static void testCoordNumToAlpha() {
        int numTests = 4;
        int passed = numTests;
        boolean res;
        if((res = Battleship.coordNumToAlpha(17576).equalsIgnoreCase("BAAA")) != true) {
            System.out.println("Battleship.coordNumToAlpha(17576)) != \"BAAA\", but " + res);
            passed--;
        }
        if((res = Battleship.coordNumToAlpha(11506714).equalsIgnoreCase("ZERTY"))!= true) {
            System.out.println("FAILED: Battleship.coordNumToAlpha(11506714)) != \"ZERTY\", but " + res);
            passed--;
        }
        if((res = Battleship.coordNumToAlpha(11506714).equalsIgnoreCase("zerty"))!= true) {
            System.out.println("Battleship.coordNumToAlpha(11506714)) != \"zerty\", but " + res);
            passed--;
        }
        if((res = Battleship.coordNumToAlpha(-14747).equalsIgnoreCase("&é\"")) != true) {
            System.out.println("FAILED: Battleship.coordNumToAlpha(-14747)) != \"&é\\\"\") != -14747, but " + res);
            passed--;
        }
        System.out.println("testCoordAlphatoNum: Passed " + passed + " of " + numTests + " tests.");
    }
    
    private static void testCheckWater() {
        int numTests = 3;
        int passed = numTests;
        int res;

        if((res = Battleship.checkWater(boardDim, 5, 8, 2, true)) != 1) {
            System.out.println("FAILED: Battleship.checkWater( boardDim, 2, 4, 2, true)) != 1, but " + res);
            passed--;
        }
        if((res = Battleship.checkWater(boardDim, 2, 4, 2, false)) != 1) {
            System.out.println("FAILED: Battleship.checkWater( boardDim, 2, 4, 2, false)) != 1, but " + res);
            passed--;
        }
        if((res = Battleship.checkWater( boardDim, 2, 4, 5, false)) != -2) {
            System.out.println(" Battleship.checkWater( boardDim, 2, 4, 5, false)) != -2, but " + res);
            passed--;
        }
        
        
        System.out.println("testCheckWater: Passed " + passed + " of " + numTests + " tests.");
    }
    
    private static void testPlaceShip() {
        int numTests = 3;
        int passed = numTests;
        boolean res;

        if((res = Battleship.placeShip( boardDim, 2, 4, 2, true, 1)) != true) {
            System.out.println("FAILED: Battleship.placeShip( boardDim, 2, 4, 2, true)) != 1, but " + res);
            passed--;
        }
        if((res = Battleship.placeShip( boardDim, 2, 4, 2, false, 2)) != true) {
            System.out.println("FAILED: Battleship.placeShip( boardDim, 2, 4, 2, false)) != 1, but " + res);
            passed--;
        }
        if((res = Battleship.placeShip( boardDim, 4, 4, 5, false, 3)) != false) {
            System.out.println("FAILED: Battleship.placeShip( boardDim, 2, 4, 5, false)) != -2, but " + res);
            passed--;
        }
        
        
        System.out.println("testPlaceShip: Passed " + passed + " of " + numTests + " tests.");
        
        //Battleship.printBoard(boardDim, "My Ship");
    }
    
    
    private static void testPlaceRandomShip() {
        int numTests = 3;
        int passed = numTests;
        boolean res;

        Random rand = new Random(Config.SEED);
        
        if((res = Battleship.placeRandomShip(boardDim, 2, 1, rand)) != true) {
            System.out.println("FAILED: Battleship.placeRandomShip(boardDim, 2, 1, rand)) != true, but " + res);
            passed--;
        }
        if((res = Battleship.placeRandomShip(boardDim, 4, 2, rand)) != true) {
            System.out.println("FAILED: Battleship.placeRandomShip(boardDim, 4, 2, rand)) != true, but " + res);
            passed--;
        }
        if((res = Battleship.placeRandomShip(boardDim, 6, 3, rand)) != false) { // FALSE because ship size is 6, and given seed, it will always be out of bound
            System.out.println("FAILED: Battleship.placeRandomShip(boardDim, 6, 3, rand)) != true, but " + res);
            passed--;
        }
        
        
        System.out.println("testPlaceRandomShip: Passed " + passed + " of " + numTests + " tests.");
    }
    
    private static void testTakeShot() {
        //FIXME
    }
    
    private static void testCheckLost() {
        //FIXME
    }
    
    private static void testPromptInt(){
        int numTests = 4;
        int passed = numTests;
        Scanner sc = new Scanner(System.in);
        
        int height = Battleship.promptInt(sc, "height", Config.MIN_HEIGHT, Config.MAX_HEIGHT );
        
    }
    
    private static void testPromptChar(){
        //FIXME
    }
    
    private static void testPromptStr(){
        int numTests = 4;
        int passed = numTests;
        Scanner sc = new Scanner(System.in);
        
        String height = Battleship.promptStr(sc, "height", "A", "Z" );
    }


}
