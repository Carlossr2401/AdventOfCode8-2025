package software.aoc.day8.b;

import java.util.List;

public record Solver(JunctionBoxList allBoxes, List<Connection> allConnections) {

    public long solveProblem() {
        JunctionBoxList currentBoxes = this.allBoxes;
        final int TOTAL_BOXES = currentBoxes.size();
        int effectiveUnions = 0;

        long lastX1 = 0;
        long lastX2 = 0;

        for (Connection c : allConnections) {
            if (effectiveUnions >= TOTAL_BOXES - 1) {
                break;
            }

            FindResult resultA = currentBoxes.getRoot(c.idA());
            JunctionBox rootA = resultA.root();

            FindResult resultB = resultA.compressedList().getRoot(c.idB());
            JunctionBox rootB = resultB.root();

            if (rootA.id() != rootB.id()) {

                currentBoxes = currentBoxes.union(c.idA(), c.idB());

                JunctionBox boxA = currentBoxes.get(c.idA());
                JunctionBox boxB = currentBoxes.get(c.idB());

                lastX1 = boxA.x();
                lastX2 = boxB.x();

                effectiveUnions++;
            }
        }

        System.out.println("Uniones efectivas realizadas: " + effectiveUnions); // Debería ser 999
        System.out.println("Última conexión: X1=" + lastX1 + ", X2=" + lastX2);

        return lastX1 * lastX2;
    }
}