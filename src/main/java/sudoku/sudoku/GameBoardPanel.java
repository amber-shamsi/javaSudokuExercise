package sudoku.sudoku;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    // Game objects
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);
            }
        }

        CellInputListener listener = new CellInputListener();

        // Adds a listener per editable cell
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    // Generate new puzzle and reset game board
    public synchronized void newGame() {
        // Generate a new puzzle
        Puzzle.newPuzzle();

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(Puzzle.getNumbers(row, col), Puzzle.getGiven(row, col));
            }
        }
    }

    // Return true if the puzzle is solved
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // Check guess validity on enter
    private class CellInputListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Cell sourceCell = (Cell)e.getSource();
            int numberEntered= Integer.parseInt(sourceCell.getText());

            sourceCell.checkGuess(numberEntered);
            sourceCell.paint();
        
            if(isSolved()){
                JOptionPane.showMessageDialog(null, "Wowee you did it!");
                SudokuApplication.board.newGame();
            }
        }
    }

    /*private class CellInputListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Cell sourceCell = (Cell)e.getSource();
            int numberEntered = Integer.parseInt(sourceCell.getText());

            System.out.println("You entered " + numberEntered);

        if (numberEntered == sourceCell.number) {
           sourceCell.status = CellStatus.CORRECT_GUESS;
        }else{
            sourceCell.status = CellStatus.WRONG_GUESS;
        }
        sourceCell.paint();
        
            if(isSolved()){
                JOptionPane.showMessageDialog(null, "Wowee you did it!");
                SudokuApplication.board.newGame();
            }
        }
    } */
}