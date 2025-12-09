// JunctionBoxList.java
package software.aoc.day8.b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record JunctionBoxList(Map<Integer, JunctionBox> boxes) {

    public int size() {
        return boxes.size();
    }

    public JunctionBox get(int id) {
        return boxes.get(id);
    }

    public JunctionBoxList modifyBox(int id, int newParentId, int newCircuitSize) {
        Map<Integer, JunctionBox> newBoxesMap = new HashMap<>(this.boxes);
        JunctionBox oldBox = newBoxesMap.get(id);

        JunctionBox newBox = new JunctionBox(
                oldBox.id(),
                oldBox.x(),
                oldBox.y(),
                oldBox.z(),
                newParentId,
                newCircuitSize
        );

        newBoxesMap.put(id, newBox);

        return new JunctionBoxList(newBoxesMap);
    }

    public FindResult getRoot(int boxId) {
        return findRootAndCompress(boxId, this);
    }

    private FindResult findRootAndCompress(int boxId, JunctionBoxList currentList) {
        JunctionBox box = currentList.boxes.get(boxId);

        if (box.parentId() == box.id()) {
            return new FindResult(box, currentList);
        }

        FindResult result = findRootAndCompress(box.parentId(), currentList);
        JunctionBox root = result.root();
        JunctionBoxList listAfterCompression = result.compressedList();

        if (box.parentId() != root.id()) {

            JunctionBoxList fullyCompressedList = listAfterCompression.modifyBox(
                    boxId,
                    root.id(),
                    box.circuitSize()
            );
            return new FindResult(root, fullyCompressedList);
        }

        return result;
    }


    public JunctionBoxList union(int idA, int idB) {

        FindResult resultA = this.getRoot(idA);
        JunctionBox rootA = resultA.root();

        FindResult resultB = resultA.compressedList().getRoot(idB);
        JunctionBox rootB = resultB.root();

        JunctionBoxList workingList = resultB.compressedList();

        if (rootA.id() == rootB.id()) {
            return workingList;
        }

        JunctionBox smallerRoot;
        JunctionBox largerRoot;

        if (rootA.circuitSize() < rootB.circuitSize()) {
            smallerRoot = rootA;
            largerRoot = rootB;
        } else {
            smallerRoot = rootB;
            largerRoot = rootA;
        }

        return applyUnionModifications(smallerRoot, largerRoot, workingList);
    }

    private JunctionBoxList applyUnionModifications(JunctionBox smallerRoot, JunctionBox largerRoot, JunctionBoxList initialState) {
        JunctionBoxList workingList = initialState;

        workingList = workingList.modifyBox(
                smallerRoot.id(),
                largerRoot.id(),
                smallerRoot.circuitSize()
        );

        int newLargeSize = smallerRoot.circuitSize() + largerRoot.circuitSize();

        workingList = workingList.modifyBox(
                largerRoot.id(),
                largerRoot.id(),
                newLargeSize
        );
        return workingList;
    }

    public List<Integer> getFinalCircuitSizes() {
        Map<Integer, Integer> finalSizesMap = new HashMap<>();

        System.out.println(this);

        for (JunctionBox box : boxes.values()) {

            if (box.id() == box.parentId()) {

                finalSizesMap.put(box.id(), box.circuitSize());
            }
        }

        // 4. Devolver la lista de los tamaños.
        return new ArrayList<>(finalSizesMap.values());
    }
}