import java.util.*;

public class ProgramRepository {
    public Map<String, int[]> build() {
        Map<String, int[]> repo = new HashMap<>();

        // Programa 1 - sequência simples
        repo.put("prog1", new int[] { 10, 20, 30, 40, 50, 60, 70, 80 });

        // Programa 2 - valores maiores
        repo.put("prog2", new int[] { 100, 200, 300, 400, 500 });

        // Programa 3 - pseudo-código de exemplo
        repo.put("prog3", new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });

        return repo;
    }
}
