import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * 
 * @author Marc Renault
 * @author Ajmain Naqib
 *
 */
public class Battleship {

    static int height, width, xRange, yRange, xRangeEnemy, yRangeEnemy, xCoord, yCoord, shipLength,
    spaceExists, shipID, shipNum;
static char loop, orientationInput, tryAgain;
public static int i, j;
static boolean orientation, shipPlaced, randomShipPlaced, tryAgainBool, addShipStatus, winnerFound;
static char userBoard[][], enemyBoard[][], trackBoard[][];
    // static String log;

    /**
     * This method converts a String representing a base (or radix) 26 number into a decimal (or
     * base 10) number. The String representation of the base 26 number uses the letters of the
     * Latin alphabet to represent the 26 digits. That is, A represents 0, B represents 1, C
     * represents 2, ..., Y represents 24, and Z represents 25.
     *
     * A couple of examples: BAAA = 1 * 26^3 + 0 * 26^2 + 0 * 26^1 + 0 * 26^0 = 17576 ZERTY = 25 *
     * 26^4 + 4 * 26^3 + 17 * 26^2 + 19 * 26^1 + 24 * 26^0 = 11506714
     *
     * For this method: - use Math.pow to calculate the powers of 26. - don't assume that the input
     * is in any particular case; use toUpperCase(). - don't check that the input is only 'A' to
     * 'Z'. - calculate the value of each digit relative to 'A'. - start from either the first or
     * last character, and calculate the exponent based on the index of each character.
     *
     * @param coord The coordinate value in base 26 as described above.
     * @return The numeric representation of the coordinate.
     */
    public static int coordAlphaToNum(String coord) {
        coord = coord.toUpperCase();
        int length = coord.length();
        char eachLetter;
        int i, j = 0, lengths, sum = 0;
        double ASCII, doubleI = 0.0;

        for (i = 0; i < length; i++) {
            eachLetter = coord.charAt(i);
            ASCII = eachLetter - 'A'; // Relative to 'A'.
            lengths = length - i - 1;
            doubleI = (double) (lengths); // Casting a double from integer.
            sum = sum + (int) ((ASCII * Math.pow(26, doubleI))); //

            // System.out.println("Debug: i=" + i);
            // System.out.println("Debug: char=" + eachletter);
            // System.out.println("Debug: ASCII=" + ASCII);
            // System.out.println("Debug: sum=" + sum);
        }
        return sum;
    }

    /**
     * This method converts an int value into a base (or radix) 26 number, where the digits are
     * represented by the 26 letters of the Latin alphabet. That is, A represents 0, B represents 1,
     * C represents 2, ..., Y represents 24, and Z represents 25. A couple of examples: 17576 is
     * BAAA, 11506714 is ZERTY.
     *
     * The algorithm to convert an int to a String representing these base 26 numbers is as follows:
     * - Initialize res to the input integer - The next digit is determined by calculating the
     * remainder of res with respect to 26 - Convert this next digit to a letter based on 'A' - Set
     * res to the integer division of res and 26 - Repeat until res is 0
     *
     * @param coord The integer value to covert into an alpha coordinate.
     * @return The alpha coordinate in base 26 as described above. If coord is negative, an empty
     *         string is returned.
     */
    public static String coordNumToAlpha(int coord) {
        String result = "";
        int num = Math.abs(coord);
        if (num == 0) {
            result = "A";
        }

        while (num > 0 || num < 0) {
            int remainder = num % 26;   //Finding remainder 
            char digit = (char) (remainder + 'A');  //Casting char from int, relative to A.
            result = digit + result;    //Add digit to the string initialized.
            num = (num - remainder) / 26;   //Calculation to keep running while loop.
        }
        // System.out.println("Debug: result=" + result);
        return result;
    }

    /**
     * Prompts the user for an integer value, displaying the following: "Enter the valName (min to
     * max): " Note: There should not be a new line terminating the prompt. valName should contain
     * the contents of the String referenced by the parameter valName. min and max should be the
     * values passed in the respective parameters.
     *
     * After prompting the user, the method will read an int from the console and consume an entire
     * line of input. If the value read is between min and max (inclusive), that value is returned.
     * Otherwise, "Invalid value." terminated by a new line is output and the user is prompted
     * again.
     *
     * @param sc The Scanner instance to read from System.in.
     * @param valName The name of the value for which the user is prompted.
     * @param min The minimum acceptable int value (inclusive).
     * @param min The maximum acceptable int value (inclusive).
     * @return Returns the value read from the user.
     */
    public static int promptInt(Scanner sc, String valName, int min, int max) {
        // System.out.println("Debug.promtInt: y cord STRING: "+ valName);

        System.out.print("Enter the " + valName + " (" + min + " to " + max + "): ");
        int input = sc.nextInt();

        while ((input < min) || (input > max)) {    //Makes sure input is in range.
            System.out.println("Invalid value.");
            System.out.print("Enter the " + valName + " (" + min + " to " + max + "): ");
            input = sc.nextInt();
        }
        return input;
    }

    /**
     * Prompts the user for an String value, displaying the following: "Enter the valName (min to
     * max): " Note: There should not be a new line terminating the prompt. valName should contain
     * the contents of the String referenced by the parameter valName. min and max should be the
     * values passed in the respective parameters.
     *
     * After prompting the user, the method will read an entire line of input, trimming any trailing
     * or leading whitespace. If the value read is (lexicographically ignoring case) between min and
     * max (inclusive), that value is returned. Otherwise, "Invalid value." terminated by a new line
     * is output and the user is prompted again.
     *
     * @param sc The Scanner instance to read from System.in.
     * @param valName The name of the value for which the user is prompted.
     * @param min The minimum acceptable String value (inclusive).
     * @param min The maximum acceptable String value (inclusive).
     * @return Returns the value read from the user.
     */
    public static String promptStr(Scanner sc, String valName, String min, String max) {
        System.out.print("Enter the " + valName + " (" + min + " to " + max + "): ");
        String input = sc.next();   //Taking the next object
        input += sc.nextLine(); //Makes sure you add entire line to input.
        input = input.trim();

        int valueInput = coordAlphaToNum(input);
        int valueMin = coordAlphaToNum(min);
        int valueMax = coordAlphaToNum(max);

        while ((valueInput < valueMin) || (valueInput > valueMax)) {    //Makes sure valueInput is in range.
            System.out.println("Invalid value.");
            System.out.print("Enter the " + valName + " (" + min + " to " + max + "): ");
            input = sc.nextLine().trim();
            valueInput = coordAlphaToNum(input);
        }
        return input;
    }

    /**
     * Prompts the user for an char value. The prompt displayed is the contents of the String
     * referenced by the prompt parameter. Note: There should not be a new line terminating the
     * prompt.
     *
     * After prompting the user, the method will read an entire line of input and return the first
     * non-whitespace character in lower case.
     *
     * @param sc The Scanner instance to read from System.in
     * @param prompt The user prompt.
     * @return Returns the first non-whitespace character (in lower case) read from the user. If
     *         there are no non-whitespace characters read, the null character is returned.
     */
    public static char promptChar(Scanner sc, String prompt) {
        System.out.print(prompt);

        String input = sc.next();   //Taking the next object
        input += sc.nextLine(); //Makes sure you add entire line to input.
        input = input.trim();
        input = input.toLowerCase();

        // System.out.println("DEBUG: input =" + input);
        char character = input.charAt(0);

        return character;
    }

    /**
     * Initializes a game board so that all the entries are Config.WATER_CHAR.
     *
     * @param board The game board to initialize.
     */
    public static void initBoard(char board[][]) {

        int yRange = board.length; // Board Height
        int xRange = board[0].length; // Board Width

        for (int i = 0; i < xRange; i++) { 
            for (int j = 0; j < yRange; j++) { 
                board[j][i] = Config.WATER_CHAR;
            }
        }
    }

    /**
     * Prints the game boards as viewed by the user. This method is used to print the game boards as
     * the user is placing their ships and during the game play.
     *
     * Some notes on the display: - Each column printed will have a width of Config.MAX_COL_WIDTH. -
     * Each row is followed by an empty line. - The values in the headers and cells are to be right
     * justified.
     *
     * @param board The board to print.
     * @param caption The board caption.
     */
    public static void printBoard(char board[][], String caption) {
        int yRange = board.length; // Board Height
        int xRange = board[0].length; // Board Width
        System.out.println(caption);
        System.out.print("   ");
        
        for (int j = 0; j < xRange; j++) {
            System.out.print("" + "  " + coordNumToAlpha(j)); // Prints A B C D...
        }
        
        System.out.println();
        
        for (int i = 0; i < yRange; i++) {
            System.out.print("  ");
            System.out.print("" + i + "");  // Prints Row number
            
            for (int j = 0; j < xRange; j++) {
                System.out.print("  " + board[i][j]);
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Determines if a sequence of cells of length len in a game board is clear or not. This is used
     * to determine if a ship will fit on a given game board. The x and y coordinates passed in as
     * parameters represent the top-left cell of the ship when considering the grid.
     * 
     * @param board The game board to search.
     * @param xcoord The x-coordinate of the top-left cell of the ship.
     * @param ycoord The y-coordinate of the top-left cell of the ship.
     * @param len The length of the ship.
     * @param dir true if the ship will be vertical, otherwise horizontal
     * @return 1 if the cells to be occupied by the ship are all Config.WATER_CHAR, -1 if the cells
     *         to be occupied are not Config.WATER_CHAR, and -2 if the ship would go out-of-bounds
     *         of the board.
     */
    public static int checkWater(char board[][], int xcoord, int ycoord, int len, boolean dir) {
        if ((xcoord >= 0 && xcoord <= (board[0].length - 1))
            && (ycoord >= 0 && ycoord <= board.length - 1)) {   //Enter if in-bound

            if (dir == true) {  //Enter if vertical
                if ((ycoord + (len - 1) <= board.length - 1)) { //Consider the length of the ship
                    for (int i = 0; i < len; i++) {
                        if (board[ycoord + i][xcoord] != Config.WATER_CHAR) {
                            return -1;
                        }
                    }
                    return 1;
                }
            }

            if (dir == false) { //Enter if horizontal
                if ((xcoord + (len - 1) <= board[0].length - 1)) {  //Consider the width of the ship
                    for (int i = 0; i < len; i++) {
                        if (board[ycoord][xcoord + i] != Config.WATER_CHAR) {
                            return -1;
                        }
                    }
                    return 1;
                }
            }
        }
        return -2;
    }

    /**
     * Checks the cells of the game board to determine if all the ships have been sunk.
     *
     * @param board The game board to check.
     * @return true if all the ships have been sunk, false otherwise.
     */
    public static boolean checkLost(char board[][]) {
        int i, j;
        int yRange = board.length; // Board Height
        int xRange = board[0].length; // Board Width

        for (i = 0; i < xRange; i++) { 
            for (j = 0; j < yRange; j++) {
                if ((takeShot(board, i, j)) == 1) { //Check every possible coord
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places a ship into a game board. The coordinate passed in the parameters xcoord and ycoord
     * represent the top-left coordinate of the ship. The ship is represented on the game board by
     * the Character representation of the ship id. (For this method, you can assume that the id
     * parameter will only be values 1 through 9.)
     *
     * @param board The game board to search.
     * @param xcoord The x-coordinate of the top-left cell of the ship.
     * @param ycoord The y-coordinate of the top-left cell of the ship.
     * @param len The length of the ship.
     * @param dir true if the ship will be vertical, otherwise horizontal.
     * @param id The ship id, assumed to be 1 to 9.
     * @return false if the ship goes out-of-bounds of the board, true otherwise.
     */
    public static boolean placeShip(char board[][], int xcoord, int ycoord, int len, boolean dir,
        int id) {
        int i, j;
        char ship = (char) (id + 48);
        int yRange = board.length; // Board Height
        int xRange = board[0].length; // Board Width
        int shipLength = len;

        try {
            if (dir) { // Vertical Ship
                if (yRange < (ycoord + shipLength)) {
                    return false; // the ship is out-of-bounds of the board.
                }
                for (i = 0; i < shipLength; i++) {
                    board[ycoord][xcoord] = ship;
                    ycoord = ycoord + 1;
                }
            } else { // Horizontal Ship
                if (xRange < (xcoord + shipLength)) {
                    return false; // the ship is out-of-bounds of the board.
                }
                for (i = 0; i < shipLength; i++) {
                    board[ycoord][xcoord] = ship;   //Change char of the board to ship
                    xcoord = xcoord + 1;
                    // System.out.println(board[xcoord][ycoord]);
                }
            }
            return true;
        } catch (Exception e) { //If out-of-bound, most-likely index error
            return false;
        }
    }

    /**
     * Randomly attempts to place a ship into a game board. The random process is as follows: 1 -
     * Pick a random boolean, using rand. True represents vertical, false horizontal. 2 - Pick a
     * random integer, using rand, for the x-coordinate of the top-left cell of the ship. The number
     * of integers to choose from should be calculated based on the width of the board and length of
     * the ship such that the placement of the ship won't be out-of-bounds. 3 - Pick a random
     * integer, using rand, for the y-coordinate of the top-left cell of the ship. The number of
     * integers to choose from should be calculated based on the height of the board and length of
     * the ship such that the placement of the ship won't be out-of-bounds. 4 - Verify that this
     * random location can fit the ship without intersecting another ship (checkWater method). If
     * so, place the ship with the placeShip method.
     *
     * It is possible for the configuration of a board to be such that a ship of a given length may
     * not fit. So, the random process will be attempted at most Config.RAND_SHIP_TRIES times.
     * 
     * @param board The game board to search.
     * @param len The length of the ship.
     * @param id The ship id, assumed to be 1 to 9..
     * @param rand The Random object.
     * @return true if the ship is placed successfully, false otherwise.
     */
    public static boolean placeRandomShip(char board[][], int len, int id, Random rand) {

        int yRange = board.length; // Board Height
        int xRange = board[0].length; // Board Width
        int tries = 0;
        int shipLength = len;
        boolean orientation;
        int xCoord, yCoord, spaceExists;

        do {
            orientation = rand.nextBoolean();

            if (orientation) { // Vertical Ship
                xCoord = rand.nextInt((xRange));
                yCoord = rand.nextInt((yRange - len + 1));
            } else { // Horizontal Ship
                xCoord = rand.nextInt((xRange - len + 1));
                yCoord = rand.nextInt((yRange));
            }

            spaceExists = checkWater(board, xCoord, yCoord, shipLength, orientation);
            tries++;

        } while ((spaceExists != 1) && (tries < Config.RAND_SHIP_TRIES));  // Make sure doesn't try more 
                                                                           // than 20
        if (spaceExists == 1) { //If coord is vacant
            placeShip(board, xCoord, yCoord, shipLength, orientation, id);  
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method interacts with the user to place a ship on the game board of the human player and
     * the computer opponent. The process is as follows: 1 - Print the user primary board, using the
     * printBoard. 2 - Using the promptChar method, prompt the user with "Vertical or horizontal?
     * (v/h) ". A response of v is interpreted as vertical. Anything else is assumed to be
     * horizontal. 3 - Using the promptInt method, prompt the user for an integer representing the
     * "ship length", where the minimum ship length is Config.MIN_SHIP_LEN and the maximum ship
     * length is width or height of the game board, depending on the input of the user from step 1.
     * 4 - Using the promptStr method, prompt the user for the "x-coord". The maximum value should
     * be calculated based on the width of the board and the length of the ship. You will need to
     * use the coordAlphaToNum and coordNumToAlpha methods to covert between int and String values
     * of coordinates. 5 - Using the promptInt method, prompt the user for the "y-coord". The
     * maximum value should be calculated based on the width of the board and the length of the
     * ship. 6 - Check if there is space on the board to place the ship. 6a - If so: - Place the
     * ship on the board using placeShip. - Then, call placeRandomShip to place the opponents ships
     * of the same length. - If placeRandomShip fails, print out the error message (terminated by a
     * new line): "Unable to place opponent ship: id", where id is the ship id, and return false. 6b
     * - If not: - Using promptChar, prompt the user with "No room for ship. Try again? (y/n): " -
     * If the user enters a 'y', restart the process at Step 1. - Otherwise, return false.
     *
     * @param sc The Scanner instance to read from System.in.
     * @param boardPrime The human player board.
     * @param boardOpp The opponent board.
     * @param id The ship id, assumed to be 1 to 9.
     * @param rand The Random object.
     * @return true if ship placed successfully by player and computer opponent, false otherwise.
     */
    public static boolean addShip(Scanner sc, char boardPrime[][], char boardOpp[][], int id,
        Random rand) {
        int yRange = boardPrime.length; // Board Height
        int xRange = boardPrime[0].length; // Board Width
        int yRangeEnemy = boardOpp.length; // Board Height
        int xRangeEnemy = boardOpp[0].length; // Board Width
        String xCoordString = null;
        String xCoordMin, xCoordMax;
        boolean tryAgainBool = true;
        boolean orientation, shipPlaced = false, randomShipPlaced = false;
        char orientationInput, tryAgain, loop = 0;
        int shipLength, xCoord, yCoord, spaceExists;

        while (tryAgainBool) {
            printBoard(boardPrime, "My Ships:");

            orientationInput = promptChar(sc, "Vertical or horizontal? (v/h): ");
            if (orientationInput == 'v') { // Vertical
                orientation = true;
                shipLength = promptInt(sc, "ship length", Config.MIN_SHIP_LEN, yRange);

                xCoordMin = coordNumToAlpha(0); //A
                xCoordMax = coordNumToAlpha(xRange - 1);    //Letter of Range- 1 
                xCoordString = promptStr(sc, "x-coord", xCoordMin, xCoordMax);
                xCoord = coordAlphaToNum(xCoordString);
                yCoord = promptInt(sc, "y-coord", 0, (yRange - shipLength));


            } else { // Horizontal
                orientation = false;
                shipLength = promptInt(sc, "ship length", Config.MIN_SHIP_LEN, xRange);
                xCoordMin = coordNumToAlpha(0);
                xCoordMax = coordNumToAlpha(xRange - shipLength);
                xCoordString = promptStr(sc, "x-coord", xCoordMin, xCoordMax);
                xCoord = coordAlphaToNum(xCoordString);
                yCoord = promptInt(sc, "y-coord", 0, (yRange - 1));
            }

            spaceExists = checkWater(boardPrime, xCoord, yCoord, shipLength, orientation);

            if (spaceExists == 1) {
                shipPlaced = placeShip(boardPrime, xCoord, yCoord, shipLength, orientation, id);
                randomShipPlaced = placeRandomShip(boardOpp, shipLength, id, rand);

                if (!randomShipPlaced) {
                    System.out.println("Unable to place opponent ship: " + id);
                    return false;
                }
                tryAgainBool = false;
            } else if (spaceExists == -1) {
                tryAgain = promptChar(sc, "No room for ship. Try again? (y/n): ");

                if (tryAgain == 'y') {
                    tryAgainBool = true;
                } else if (loop == 'n') {
                    return false;
                }

            } else {
                tryAgainBool = false;
            }
        }

        if (shipPlaced && randomShipPlaced) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks the state of a targeted cell on the game board. This method does not change the
     * contents of the game board.
     *
     * @return 3 if the cell was previously targeted. 2 if the shot would be a miss. 1 if the shot
     *         would be a hit. -1 if the shot is out-of-bounds.
     */
    public static int takeShot(char[][] board, int x, int y) {


        int xcoord = x;
        int ycoord = y;


        try {
            if (board[ycoord][xcoord] == Config.WATER_CHAR) {
                return 2;
            }

            if ((board[ycoord][xcoord] == Config.HIT_CHAR)
                || (board[ycoord][xcoord] == Config.MISS_CHAR)) {
                return 3;
            }

            for (int i = 1; i < 10; i++) {
                char ship = (char) (i + 48);

                if ((board[ycoord][xcoord]) == ship) {
                    return 1;
                }

            }

        } catch (Exception e) {
            return -1;
        }
        return 0;

    }

    /**
     * Interacts with the user to take a shot. The procedure is as follows: 1 - Using the promptStr
     * method, prompt the user for the "x-coord shot". The maximum value should be based on the
     * width of the board. You will need to use the coordAlphaToNum and coordNumToAlpha methods to
     * covert between int and String values of coordinates. 2 - Using the promptInt method, prompt
     * the user for the "y-coord shot". The maximum value should be calculated based on the width of
     * the board. 3 - Check the shot, using the takeShot method. If it returns: -1: Print out an
     * error message "Coordinates out-of-bounds!", terminated by a new line. 3: Print out an error
     * message "Shot location previously targeted!", terminated by a new line. 1 or 2: Update the
     * cells in board and boardTrack with Config.HIT_CHAR or Config.MISS_CHAR accordingly. This
     * process should repeat until the takeShot method returns 1 or 2.
     *
     * @param sc The Scanner instance to read from System.in.
     * @param board The computer opponent board (containing the ship placements).
     * @param boardTrack The human player tracking board.
     */
    public static void shootPlayer(Scanner sc, char[][] board, char[][] boardTrack) {



        int yRange = boardTrack.length; // Board Height
        int xRange = boardTrack[0].length; // Board Width
        String xCoordString = null;
        String xCoordMin, xCoordMax;


        xCoordMin = coordNumToAlpha(0);
        xCoordMax = coordNumToAlpha(xRange - 1);
        xCoordString = promptStr(sc, "x-coord shot", xCoordMin, xCoordMax);
        int xCoord = coordAlphaToNum(xCoordString);

        int yCoord = promptInt(sc, "y-coord shot", 0, (yRange - 1));

        int res = takeShot(board, xCoord, yCoord);

        if (res == -1) {
            System.out.println("Coordinates out-of-bounds!");
        }

        if (res == -3) {
            System.out.println("Shot location previously targeted!");
        }

        if (res == 1) {
            boardTrack[yCoord][xCoord] = Config.HIT_CHAR;
            board[yCoord][xCoord] = Config.HIT_CHAR;
        }

        if (res == 2) {
            boardTrack[yCoord][xCoord] = Config.MISS_CHAR;
            board[yCoord][xCoord] = Config.MISS_CHAR;
        }


    }

    /**
     * Takes a random shot on the game board. The random process works as follows: 1 - Pick a random
     * valid x-coordinate 2 - Pick a random valid y-coordinate 3 - Check the shot, using the
     * takeShot method. This process should repeat until the takeShot method returns 1 or 2, then
     * update the cells in board with Config.HIT_CHAR or Config.MISS_CHAR accordingly.
     *
     * Note: Unlike the placeRandomShip method, this method continues until it is successful. This
     * may seem risky, but in this case the random process will terminate (find an untargeted cell)
     * fairly quickly. For more details, see the appendix of the Big Program 1 subject.
     *
     * @param rand The Random object.
     * @param board The human player game board.
     */
    public static void shootComputer(Random rand, char[][] board) {

        int yRange = board.length; // Board Height
        int xRange = board[0].length; // Board Width
        int xCoord;
        int yCoord;
        int res;

        do {
            xCoord = rand.nextInt((xRange));
            yCoord = rand.nextInt((yRange));
            res = takeShot(board, xCoord, yCoord);

            // log += "(" + xCoord + ", " + yCoord+ "), ";
        } while (!(res == 1 || res == 2));



        if (res == 1) {
            board[yCoord][xCoord] = Config.HIT_CHAR;
        }

        if (res == 2) {
            board[yCoord][xCoord] = Config.MISS_CHAR;
        }


    }

    /**
     * This is the main method for the Battleship game. It consists of the main game and play again
     * loops with calls to the various supporting methods. When the program launches (prior to the
     * play again loop), a message of "Welcome to Battleship!", terminated by a newline, is
     * displayed. After the play again loop terminiates, a message of "Thanks for playing!",
     * terminated by a newline, is displayed.
     *
     * The Scanner object to read from System.in and the Random object with a seed of Config.SEED
     * will be created in the main method and used as arguments for the supporting methods as
     * required.
     *
     * Also, the main method will require 3 game boards to track the play: - One for tracking the
     * ship placement of the user and the shots of the computer, called the primary board with a
     * caption of "My Ship". - One for displaying the shots (hits and misses) taken by the user,
     * called the tracking board with a caption of "My Shots"; and one for tracking the ship
     * placement of the computer and the shots of the user. - The last board is never displayed, but
     * is the primary board for the computer and is used to determine when a hit or a miss occurs
     * and when all the ships of the computer have been sunk. Notes: - The size of the game boards
     * are determined by the user input. - The game boards are 2d arrays that are to be viewed as
     * row-major order. This means that the first dimension represents the y-coordinate of the game
     * board (the rows) and the second dimension represents the x-coordinate (the columns).
     *
     * @param args Unused.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // File file = new File("C:/Temp/JAVA/Battleship/src/INPUT.txt");
        // Scanner sc = new Scanner(file);
        Scanner sc = new Scanner(System.in);
        Random rand = new Random(Config.SEED);

        char userBoard[][], enemyBoard[][], trackBoard[][];
        int height, width, shipNum;
        char loop;
        boolean winnerFound = false;
        boolean loopStatus = true;
        boolean addShipStatus = true;
        String playAgain = "Would you like to play again? (y/n): ";


        System.out.println("Welcome to Battleship!");
        while (loopStatus) {
            height = promptInt(sc, "board height", Config.MIN_HEIGHT, Config.MAX_HEIGHT);
            width = promptInt(sc, "board width", Config.MIN_WIDTH, Config.MAX_WIDTH);
            // height = 8;
            // width = 5;
            System.out.println();

            userBoard = new char[height][width];
            trackBoard = new char[height][width];
            enemyBoard = new char[height][width];
            initBoard(userBoard);
            initBoard(trackBoard);
            initBoard(enemyBoard);

            shipNum = promptInt(sc, "number of ships", Config.MIN_SHIPS, Config.MAX_SHIPS);
            // shipNum = 3;

            for (int idOfShip = 1; idOfShip <= shipNum; idOfShip++) {
                addShipStatus = addShip(sc, userBoard, enemyBoard, idOfShip, rand);
                // System.out.println("Debug.MAIN i: "+ i);
                if (!addShipStatus) {
                    loop = promptChar(sc, "Error adding ships. Restart game? (y/n): ");
                    if (loop == 'y') {
                        loopStatus = true;
                    } else {
                        loopStatus = false;
                        break;
                    }
                }
            }

            do {
                // System.out.println("DEBUG: LOG: " + log);

                printBoard(userBoard, "My Ships:");
                printBoard(trackBoard, "My Shots:");

                // TODO: remove
                // printBoard(enemyBoard, "Enemy Ships:");

                shootPlayer(sc, enemyBoard, trackBoard);

                if (checkLost(enemyBoard)) {
                    winnerFound = true;
                    System.out.println("Congratulations, you sunk all the computer's ships!");
                    printBoard(userBoard, "My Ships:");
                    printBoard(trackBoard, "My Shots:");
                    break;
                }

                shootComputer(rand, userBoard);
                if (checkLost(userBoard)) {
                    winnerFound = true;
                    System.out.println("Oh no! The computer sunk all your ships!");
                    printBoard(userBoard, "My Ships:");
                    printBoard(trackBoard, "My Shots:");
                    break;
                }
            } while (!winnerFound);



            // System.out.println();
            loop = promptChar(sc, playAgain);

            if (loop == 'y') {
                loopStatus = true;
            } else {
                loopStatus = false;
            }
        }
        System.out.println("Thanks for playing!");
    }
}
