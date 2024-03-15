package sudoku.sudoku.solver;

import sudoku.sudoku.game.Puzzle;

public class BoardSolver {
    protected boolean cellIsValid(int number, int row, int col) {
        boolean rowIsValid = Puzzle.checkRow(col, number);
        boolean colIsValid = Puzzle.checkCol(row, number);
        boolean squareIsValid = Puzzle.checkSquare(row, col, number);

        return rowIsValid && colIsValid && squareIsValid;
    }
}
