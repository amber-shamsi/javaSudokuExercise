package sudoku.sudoku.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DosokuResponse {

    @JsonProperty("newboard")
    private NewBoard newBoard;

    public static class NewBoard{
        private List<Grid> grids;
        private int results;
        private String message;

        public List<Grid> getGrids() {
            return grids;
        }

        public void setGrids(List<Grid> grids) {
            this.grids = grids;
        }

        public int getResults() {
            return results;
        }

        public void setResults(int result) {
            this.results = result;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class Grid{
        @JsonProperty("value")
        List<List<Integer>> value;
        @JsonProperty("solution")
        List<List<Integer>> solution;

        public List<List<Integer>> getBoard() {
            return value;
        }

        public void setBoard(List<List<Integer>> value) {
            this.value = value;
        }

        public List<List<Integer>> getSolution() {
            return solution;
        }

        public void setSolution(List<List<Integer>> solution) {
            this.solution = solution;
        }
    }

    // Convert newBoard grid to array
    public int[][] toBoard() {
        if (newBoard != null && !newBoard.getGrids().isEmpty()) {
            List<List<Integer>> values = newBoard.getGrids().get(0).getBoard();
            return values.stream()
                         .map(list -> list.stream().mapToInt(i -> i).toArray())
                         .toArray(int[][]::new);
        }
        return new int[0][0];
    }

     // Convert solution grid to array
     public int[][] solutionToBoard() {
        if (newBoard != null && !newBoard.getGrids().isEmpty()) {
            List<List<Integer>> solution = newBoard.getGrids().get(0).getSolution();
            return solution.stream()
                         .map(list -> list.stream().mapToInt(i -> i).toArray())
                         .toArray(int[][]::new);
        }
        return new int[0][0];
    }
}
