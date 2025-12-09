package software.aoc.day8.b;

public record Connection(int idA, int idB, double distanceSquared) {
    public static Connection create(JunctionBox boxA, JunctionBox boxB) {
        long dx = boxA.x() - boxB.x();
        long dy = boxA.y() - boxB.y();
        long dz = boxA.z() - boxB.z();

        double distSq = (double)(dx * dx + dy * dy + dz * dz);

        return new Connection(boxA.id(), boxB.id(), distSq);
    }
}
