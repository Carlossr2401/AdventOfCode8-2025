package software.aoc.day8.a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record FileInstructionReader(String filePath) {

    public JunctionBoxList readAllData() throws IOException {

        Map<Integer, JunctionBox> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentId = 0;

            while ((line = reader.readLine()) != null) {
                map.put(currentId, createBox(line, currentId));
                currentId++;
            }
        }

        return new JunctionBoxList(map);
    }

    private JunctionBox createBox(String line, int id) {
        String[] position = line.split(",");

        long x = Long.parseLong(position[0].trim());
        long y = Long.parseLong(position[1].trim());
        long z = Long.parseLong(position[2].trim());

        return new JunctionBox(id, x, y, z, id, 1);
    }
}