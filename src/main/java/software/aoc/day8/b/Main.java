package software.aoc.day8.b;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    static void main() throws IOException {
        FileInstructionReader reader = new FileInstructionReader("src/main/resources/input");
        JunctionBoxList allBoxes = reader.readAllData();
        List<Connection> allConnections = new ArrayList<>();

        for (int i = 0; i < allBoxes.size(); i++) {
            for (int j = i + 1; j < allBoxes.size(); j++) {
                JunctionBox boxA = allBoxes.get(i);
                JunctionBox boxB = allBoxes.get(j);

                allConnections.add(Connection.create(boxA, boxB));
            }
        }

        allConnections.sort(Comparator.comparingDouble(Connection::distanceSquared));

        int connectionsVerified = 0;
        Solver solver = new Solver(allBoxes, allConnections);
        System.out.println(solver.solveProblem());
    }
}
