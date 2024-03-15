package sudoku.sudoku.game;

import java.awt.Color;
import java.awt.Font;
import java.io.Serial;
import javax.swing.JTextField;
/**
 * The Cell class models the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {
    @Serial
    private static final long serialVersionUID = 1L;

    /* Styling */
    public static final Color BG_GIVEN = Color.WHITE;
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_NOT_GIVEN = Color.GRAY;
    public static final Color BG_TO_GUESS  = Color.CYAN;
    public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
    public static final Color BG_WRONG_GUESS   = new Color(216, 0, 0);
    public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);

    int row, col;
    int number;
    int guessedNumber;
    CellStatus status;

    public Cell(int row, int col) {
        super();
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);

        this.row = row;
        this.col = col;
    }

    // Colour according to status
    public void paint() {
        if (status == CellStatus.GIVEN) {
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        }
        else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        }
        else if (status == CellStatus.CORRECT_GUESS) {
            super.setBackground(BG_CORRECT_GUESS);
        }
        else if (status == CellStatus.WRONG_GUESS) {
            super.setBackground(BG_WRONG_GUESS);
        }
    }

    // Reset cell for API
    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        paint();
    }

    // Checks row, col, square for guess validity
    public void checkGuess(int num){
        if(num == 0 || num > 9 || num < 0){
            setStatus(CellStatus.TO_GUESS);
        }else if(Puzzle.isValid(row, col, num) || num == Puzzle.getNumbers(row, col)){
            guessedNumber = num;
            status = CellStatus.CORRECT_GUESS;
        }
        else{
            guessedNumber = num;
            status = CellStatus.WRONG_GUESS;
        }
    }

    public void setStatus(CellStatus status){
        this.status = status;
    }
}