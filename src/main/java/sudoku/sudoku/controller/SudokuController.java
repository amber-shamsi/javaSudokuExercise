package sudoku.sudoku.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import sudoku.sudoku.SudokuConstants;
import sudoku.sudoku.model.DosokuResponse;

@RestController
@RequestMapping("/sudoku")
public class SudokuController {
    
    public static final int GRID_SIZE = SudokuConstants.GRID_SIZE;    
    private final static String uri = "https://sudoku-api.vercel.app/api/dosuku";
    private final static WebClient client = WebClient.create();
    private static Mono<int[][]> solution;

    @GetMapping("/get")
    public static Mono<int[][]> getNewSudokuBoard() { 
        // Fetch the puzzle board as Mono
        Mono<DosokuResponse> puzzleReponse = client.get()
                                        .uri(uri)
                                        .retrieve()
                                        .bodyToMono(DosokuResponse.class);
        
        // Convert board to array
        Mono<int[][]> puzzle = dosukoModelToBoard(puzzleReponse);
        solution = solutionToBoard(puzzleReponse);

        return puzzle;
    }

    private static Mono<int[][]> solutionToBoard(Mono<DosokuResponse> dosukuReponse) {
        return dosukuReponse.map(DosokuResponse::solutionToBoard);
    }

    private static Mono<int[][]> dosukoModelToBoard(Mono<DosokuResponse> dosukuReponse) {
        return dosukuReponse.map(DosokuResponse::toBoard);
    }

    public static  Mono<int[][]> getSolution(){
        return solution;
    }
}
