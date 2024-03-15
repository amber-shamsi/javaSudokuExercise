package sudoku.sudoku.game;

public enum CellStatus 
{
    GIVEN,         // Unchangeable value; correct
    TO_GUESS,      // Empty
    CORRECT_GUESS,
    WRONG_GUESS
}