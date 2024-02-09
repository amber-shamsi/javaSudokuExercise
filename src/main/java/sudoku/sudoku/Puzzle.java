package sudoku.sudoku;

import java.util.Random;

import sudoku.sudoku.controller.SudokuController;

public class Puzzle {
    
    private static int[][] puzzleArray;
    @SuppressWarnings("unused")
    private static int[][] solution;
    private static boolean[][] isGiven;
    private static Random random;

    public Puzzle() {
        super();
    }

    // Get puzzle with API
    public static synchronized void newPuzzle() {
        puzzleArray = SudokuController.getNewSudokuBoard().block();
        solution = SudokuController.getSolution().block();
        isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

        for(int row = 0; row < SudokuConstants.GRID_SIZE; row++){
            for(int col = 0; col < SudokuConstants.GRID_SIZE; col++){
                setCellValue(row, col);
            }
        }
    }

    // Set if player can see value ro if it must be entered
    public static synchronized void setCellValue(int row, int col){
        if(puzzleArray[row][col] != 0){
            isGiven[row][col] = true;
        }
        else{
            isGiven[row][col] = false;
        }
    }

    // Check if a manual square of the puzzle is valid.
    public static synchronized boolean isValid(int row, int col){
        int num = puzzleArray[row][col];

        boolean rowisValid = checkRow(row,col, num);
        boolean colIsValid = checkCol(row, col, num);
        boolean squareIsValid = checkSquare(row, col, num);

        if(rowisValid && colIsValid && squareIsValid){
            return true;
        } else{
            return false;
        }
    }

// Check row
public static boolean checkRow(int row, int col, int num){
    for(int rowSearch = 0; rowSearch < SudokuConstants.GRID_SIZE; rowSearch++){
        if(puzzleArray[rowSearch][col] == num){
            return false;
        }
    }
    return true;
}

// Check column
public static boolean checkCol(int row, int col, int num){
    for(int colSearch = 0; colSearch < SudokuConstants.GRID_SIZE; colSearch++){
        if(puzzleArray[row][colSearch] == num){
            return false;
        }
    }
    return true;
}

// Check square
public static boolean checkSquare(int row, int col, int num){
    int startingRow = row - row % SudokuConstants.SUBGRID_SIZE;
    int startingCol = col - col % SudokuConstants.SUBGRID_SIZE;
    for(int i = 0; i < SudokuConstants.SUBGRID_SIZE; i++){
        for(int j = 0; j < SudokuConstants.SUBGRID_SIZE; j++){
            if(puzzleArray[i+startingRow][j+startingCol] == num){
                return false;
            }
        }
    }
    return true;
}

    //#region manual sudoku board geenration

    // Get the number for given row, col
    public static int getNumbers(int row, int col){
        return puzzleArray[row][col];
    }

    // Get the isGiven boolean for given row, col
    public static boolean getGiven(int row, int col){
        return isGiven[row][col];
    }

    // Generate new puzzle with int cellsToGuess empty
    // Manual; does NOT use API
    public static synchronized void newManualPuzzle(int cellsToGuess) {
        puzzleArray = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        random = new Random();

        int totalCells = SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE;
        int cellsToGive = totalCells - cellsToGuess;

        
        for(int row = 0; row < SudokuConstants.GRID_SIZE; row++){
            for(int col = 0; col < SudokuConstants.GRID_SIZE; col++){
                setNewManualCellValue(row, col);
            }
        }

        while (cellsToGive > 0){
            int randomRow = random.nextInt(9);
            int randomCol = random.nextInt(9);

            if(!isGiven[randomRow][randomCol]){
                isGiven[randomRow][randomCol] = true;
                cellsToGive--;
            }
        }
    }

    // Set a new random value for the sudoku cell 
    // Currently invalid as not generating in the correct order
    // Needs to generate in diagonal 3x3 then fill remaining in order to generate valid puzzle
    // Not part of specs : abandoned for now
    public static synchronized void setNewManualCellValue(int row, int col){
        int randomNum = random.nextInt(9)+1;
        if(!isValid(row, col, randomNum)){
            puzzleArray[row][col] = randomNum;
        }
    }

    // Check if a manual square of the puzzle is valid.
    public static synchronized boolean isValid(int row, int col, int num){
            boolean rowisValid = checkRow(row,col,num);
            boolean colIsValid = checkCol(row, col, num);
            boolean squareIsValid = checkSquare(row, col, num);
    
            if(rowisValid && colIsValid && squareIsValid){
                return true;
            } else{
                return false;
            }
        }
    
        //#endregion
}