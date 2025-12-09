package software.aoc.day8.a;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConnectionTest {

    @Test
    public void testDistanceSquaredHelper() {
        // Arrange
        JunctionBox box1 = new JunctionBox(1, 0, 0, 0, 1, 1);
        JunctionBox box2 = new JunctionBox(2, 3, 4, 0, 2, 1);

        // Act
        Connection connection = Connection.create(box1, box2);

        // Assert
        // Distance should be 3*3 + 4*4 + 0 = 9 + 16 = 25
        assertThat(connection.distanceSquared()).isEqualTo(25.0);
        assertThat(connection.idA()).isEqualTo(1);
        assertThat(connection.idB()).isEqualTo(2);
    }

    @Test
    public void testDistanceSquaredZero() {
        // Arrange
        JunctionBox box1 = new JunctionBox(1, 10, 10, 10, 1, 1);
        JunctionBox box2 = new JunctionBox(2, 10, 10, 10, 2, 1);

        // Act
        Connection connection = Connection.create(box1, box2);

        // Assert
        assertThat(connection.distanceSquared()).isEqualTo(0.0);
    }
}
