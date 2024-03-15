package sudoku.sudoku.solver;

import sudoku.sudoku.game.Puzzle;

import java.util.concurrent.Callable;

public class RowValidator implements Callable<Boolean> {

    private final int row;

    public RowValidator(int row) {
        this.row = row;
    }
    @Override
    public Boolean call() {
        for (int i = 0; i < 9; i++) {
            if (Puzzle.getNumbers(row, i) != 0) {
                boolean valid = Puzzle.isValid(row, i, Puzzle.getNumbers(row, i));
                if (!valid) return false;
            }
        }
        return true;
    }
}