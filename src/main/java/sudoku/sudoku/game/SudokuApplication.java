package sudoku.sudoku.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.Serial;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SudokuApplication extends JFrame {

	@Serial
    private static final long serialVersionUID = 1L;
    public static GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");

	public static void main(String[] args) {
		SpringApplication.run(SudokuApplication.class, args);
	}

    // Constructor
    public SudokuApplication() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board, BorderLayout.CENTER);
        //cp.add(btnSolveGame, BorderLayout.SOUTH);

        board.newGame();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

}
