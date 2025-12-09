package software.aoc.day8.a;

import java.util.Comparator;
import java.util.List;

public record Solver(JunctionBoxList allBoxes, List<Connection> allConnections) {

    public long solveProblem() {
        List<Integer> circuitSizes = getIntegers();

        circuitSizes.sort(Comparator.reverseOrder());

        if (circuitSizes.size() < 3) {
            System.out.println(circuitSizes);
            System.err.println("Error de Lógica: Solo se encontraron " + circuitSizes.size() + " circuitos. Se requieren 3 para la multiplicación final. Esto sugiere un problema en el conteo de cajas, o un conjunto de datos muy pequeño.");
            return 0;
        }

        long size1 = circuitSizes.get(0);
        long size2 = circuitSizes.get(1);
        long size3 = circuitSizes.get(2);

        return size1 * size2 * size3;
    }

    private List<Integer> getIntegers() {
        JunctionBoxList currentBoxes = this.allBoxes;
        int effectiveConnections = 0;
        final int TARGET_CONNECTIONS = 1000;

        for (Connection c : allConnections) {

            if (effectiveConnections >= TARGET_CONNECTIONS) {
                break;
            }

            FindResult resultA = currentBoxes.getRoot(c.idA());
            JunctionBox rootA = resultA.root();

            FindResult resultB = resultA.compressedList().getRoot(c.idB());
            JunctionBox rootB = resultB.root();

            if (rootA.id() != rootB.id()) {
                currentBoxes = currentBoxes.union(c.idA(), c.idB());
            }
            effectiveConnections++;
        }

        return currentBoxes.getFinalCircuitSizes();
    }
}