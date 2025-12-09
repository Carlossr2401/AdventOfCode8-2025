package software.aoc.day8.a;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JunctionBoxListTest {

    private JunctionBoxList junctionBoxList;
    private Map<Integer, JunctionBox> initialMap;

    @Before
    public void setUp() {
        initialMap = new HashMap<>();
        // Create 3 independent boxes
        initialMap.put(1, new JunctionBox(1, 0, 0, 0, 1, 1));
        initialMap.put(2, new JunctionBox(2, 10, 0, 0, 2, 1));
        initialMap.put(3, new JunctionBox(3, 20, 0, 0, 3, 1));
        
        junctionBoxList = new JunctionBoxList(initialMap);
    }

    @Test
    public void testInitialState() {
        assertThat(junctionBoxList.size()).isEqualTo(3);
        assertThat(junctionBoxList.get(1).circuitSize()).isEqualTo(1);
        assertThat(junctionBoxList.getRoot(1).root().id()).isEqualTo(1);
    }

    @Test
    public void testUnionTwoBoxes() {
        // Act: Connect 1 and 2
        JunctionBoxList updatedList = junctionBoxList.union(1, 2);

        // Assert
        FindResult result1 = updatedList.getRoot(1);
        FindResult result2 = updatedList.getRoot(2);
        
        // They should share the same root
        assertThat(result1.root().id()).isEqualTo(result2.root().id());
        
        // The size of the circuit should be 2
        assertThat(result1.root().circuitSize()).isEqualTo(2);
        
        // Box 3 should remain independent
        assertThat(updatedList.getRoot(3).root().id()).isEqualTo(3);
        assertThat(updatedList.getRoot(3).root().circuitSize()).isEqualTo(1);
    }

    @Test
    public void testUnionThreeBoxes() {
        // Act: Connect 1-2, then 2-3
        JunctionBoxList list1 = junctionBoxList.union(1, 2);
        JunctionBoxList list2 = list1.union(2, 3);

        // Assert
        JunctionBox root1 = list2.getRoot(1).root();
        JunctionBox root2 = list2.getRoot(2).root();
        JunctionBox root3 = list2.getRoot(3).root();

        // All should share the same root
        assertThat(root1.id()).isEqualTo(root2.id());
        assertThat(root2.id()).isEqualTo(root3.id());

        // Circuit size should be 3
        assertThat(root1.circuitSize()).isEqualTo(3);
    }
    
    @Test
    public void testUnionAlreadyConnected() {
        // Act: Connect 1-2, then try 1-2 again
        JunctionBoxList list1 = junctionBoxList.union(1, 2);
        JunctionBoxList list2 = list1.union(1, 2);

        // Assert: Size should simply remain 2, no error, structure stable
        JunctionBox root = list2.getRoot(1).root();
        assertThat(root.circuitSize()).isEqualTo(2);
    }
}
