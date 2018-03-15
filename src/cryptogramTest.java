//Author: Ajmain Naqib
//Task: Print a cryptographic message from input of Words.

import java.io.*;

public class cryptogramTest {
    public static void main(String args[]) throws IOException{ //change some stuff from import
        boolean loop= true;    //boolean statement make true
        
        while(loop){ // when statement will be true, it will run again. its to loop the program
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in)); //setting up to get ready to input
        System.out.println("Enter the word:"); // prompt user to input
        String input = buff.readLine(); //takes input from user and puts it to a string called "input"
        for ( int i = 0; i < input.length(); ++i ){ // a loop where the number of character input will decide how many outputs I'll show
          char inputLetter = input.charAt(i); // char take in only letters. how many character the input has to follow my loop
          int cryptoNum = (int) inputLetter - 96;// this gives a ASCII value of the input letter. ASCII of A is 97
          
          
          if (cryptoNum == -64){ // space between a word gives the value of "-64" ASCII. 
              cryptoNum = 0; // I want it to look nicer, may 0, so lets say if spacebar is detected, print 0.
          }
          
          System.out.print( cryptoNum + " "); // now print out the modified ASCII
          
          }
        loop = true; //to make the original program loop, since its not false. 
        System.out.println(); //add an empty line
        System.out.println(); //add an empty line
      }
    }
    
    }